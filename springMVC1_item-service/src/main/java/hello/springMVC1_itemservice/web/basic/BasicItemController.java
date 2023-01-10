package hello.springMVC1_itemservice.web.basic;

import hello.springMVC1_itemservice.domain.item.Item;
import hello.springMVC1_itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor  // final을 가진 객체의 생성자를 자동으로 만들어준다.
public class BasicItemController {

    private final ItemRepository itemRepository;

    /*
    1. 생성자 주입(Constructor Injection): Spring bean으로 등록된 ItemRepository의 의존관계를 자동으로 주입한다.
        단, 여기서는 @RequiredArgsConstructor에 의해 생략되었다.
    2. @Autowired는 생성자가 하나인 경우 생략할 수 있다.
     */
    /*@Autowired
    public BasicItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }*/

    /**
     * 상품 목록 form
     */
    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";  // View를 호출하고 model을 전달: resources/basic/items.html
    }

    /**
     * 개별 상품 form
     */
    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    /**
     * 상품 추가 form
     */
    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    /**
     * 상품 추가 logic: @RequestParam 사용
     */
    // @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam int quantity,
                       Model model) {

        Item item = new Item();
        // setter 대신 Item의 생성자에 바로 입력하여도 된다.
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);
        return "basic/item";
    }

    /**
     * 상품 추가 logic: @ModelAttribute 사용(1)
     */
    // @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item) {
        // @ModelAttribute의 기능 1: Item 객체 생성 및 프로퍼티 접근법(setter 호출)이 자동으로 실행된다.
        // Item item = new Item();
        // item.setItemName(itemName);
        // item.setPrice(price);
        // item.setQuantity(quantity);

        itemRepository.save(item);

        // @ModelAttribute의 기능 2: 입력된 이름 "item"에 의해 model에 자동으로 입력된다. 이 경우 Model의 선언도 생략할 수 있다.
        // model.addAttribute("item", item);
        return "basic/item";
    }

    /**
     * 상품 추가 logic: @ModelAttribute 사용(2)
     */
    // @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) {

        itemRepository.save(item);

        // @ModelAttribute의 이름을 생략하는 경우 클래스명(Item)의 앞 글자를 소문자로 바꾼 객체(item)가 model에 입력된다.
        return "basic/item";
    }

    /**
     * 상품 추가 logic: @ModelAttribute 사용(3)
     */
    // @PostMapping("/add")
    public String addItemV4(Item item) {
        // Annotation @ModelAttribute도 생략할 수 있다!
        // * @RequestParam 생략과의 구분: 단순 타입(String, int...)이 생략되면 @RequestParam이 생략된 것으로 인지하며, 임의의 객체인 경우 @ModelAttribute가 생략된 것으로 취급한다.

        itemRepository.save(item);

        // Item의 첫 글자를 소문자로 바꾼 item이 model에 담겨 전달된다.
        return "basic/item";
    }

    /**
     * 상품 추가 logic: PRG 패턴
     */
    // @PostMapping("/add")
    public String addItemV5(Item item) {
        itemRepository.save(item);
        /*
        return "basic/item"을 쓰는 경우 상품 등록 후 새로고침을 할 때마다 중복 등록이 수행된다.
        이는 새로 고침이 마지막에 서버에 전송한 데이터를 다시 전송하는 동작이기 때문이다. 따라서 view template만 불러온 시점에선 새로 고침마다 동일한 POST가 수행된다.
        이를 방지하기 위해서는 view를 불러오는 것이 아닌, 리다이렉트로 새 GET을 요청해 상품 상세 화면을 호출하여야 한다. 이 경우 마지막으로 전송한 데이터가 GET이 되어 중복 등록이 일어나지 않는다.
        중복을 방지하기 위한 이러한 일련의 동작을 PRG(Post/Redirect/Get) 패턴이라 한다.
         */
        return "redirect:/basic/items/" + item.getId();
    }

    /**
     * 상품 추가 logic: RedirectAttributes
     */
    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        /*
        #1. 저장 후 '상품 상세'로 redirect하는 경우 저장이 잘 된 것인지 직관적으로 확인하기 힘들다. RedirectAttributes를 통해 저장 수행 시 상품 상세 화면에 '저장되었습니다'라는 문구를 띄울 수 있다.
        #2. item.getId()와 같이 URL에 변수를 더하는 경우 URL인코딩이 되지 않아, 한글 또는 white space 등을 사용하는 경우 예외가 발생할 수 있다. RedirectAttributes는 이를 해결하여 준다.
        */
        // redirectAttributes에 입력한 itemId를 return값에 바인딩할 수 있으며, 여기에 사용되지 않은 attribute는 query parameter로 전달된다.
        // 즉 이 경우 /basic/items/{itemId}?status=true
        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 상품 수정 form
     */
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute(item);

        return "basic/editForm";
    }

    /**
     * 상품 수정 logic
     */
    @PostMapping("/{itemId}/edit")
    public String editItem(@PathVariable long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);

        // return "basic/item"; -> view만을 불러오므로 URL이 바뀌지 않는다. redirect를 해야 URL이 바뀐다.
        // Spring에서의 redirect는 다음과 같이 redirect + path로 할 수 있다.
        return "redirect:/basic/items/{itemId}";
    }


    /**
     * 테스트용 데이터
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
        itemRepository.save(new Item("itemC", 30000, 10));
        itemRepository.save(new Item("itemD", 40000, 40));
    }
}
