package hello.springMVC1_itemservice.web.basic;

import hello.springMVC1_itemservice.domain.item.Item;
import hello.springMVC1_itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    /**
     * 테스트용 데이터
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
