package hello.servlet.web.springmvc.v3;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Spring MVC Controller V3:
 *
 * frontcontroller의 v4를 참고한 실무 레벨의 Spring MVC이다.
 *
 * Annotation 기반의 controller는 유연한 설계를 지원한다.
 * 1. ModelAndView가 아닌 string을 반환하여도 mapping을 수행할 수 있다(new-form).
 * 2. HttpServletRequest와 HttpServletResponse, @RequestParam을 이용한 임의의 parameter도 받을 수 있다(save, members).
 *
 * 지금까지의 controller들은 HTTP method(GET, POST, PUT, DELETE, PATCH, ...)를 구분하는 기능이 없었는데, 이는 의도치 않은 결과와 직결될 여지가 있다.
 * @RequestMapping에 method 속성을 입력하면 해당 HTTP method로만 요청할 수 있도록 제한할 수 있다.
 * (이 경우 default 속성이었던 value를 명시해 주어야 한다.)
 */
@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    // @RequestMapping(value = "/new-form", method = RequestMethod.GET)  // 아래 annotation으로 축약 가능
    @GetMapping("new-form")
    public String newForm() {
        return "new-form";
    }

    // @RequestMapping(value = "/save", method = RequestMethod.POST)  // 아래 annotation으로 축약 가능
    @PostMapping("/save")
    public String save(
            @RequestParam("username") String username,  // String username = request.getParameter("username"); 와 동일
            @RequestParam("age") int age,  // int age = Integer.parseInt(request.getParameter("age")); 와 동일(int로의 type casting도 spring에서 지원한다)
            Model model) {

        Member member = new Member(username, age);
        memberRepository.save(member);

        model.addAttribute("member", member);
        return "save-result";
    }

    // @RequestMapping(method = RequestMethod.GET)  // 아래 annotation으로 축약 가능
    @GetMapping
    public String members(Model model) {

        List<Member> members = memberRepository.findAll();

        model.addAttribute("members", members);
        return "members";
    }
}
