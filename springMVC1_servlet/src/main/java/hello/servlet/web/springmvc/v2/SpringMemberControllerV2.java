package hello.servlet.web.springmvc.v2;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Spring MVC Controller V2:
 *
 * Spring MVC V1에서 RequestMapping이 class 단위가 아닌 method 단위로 적용되는 것을 확인할 수 있다.
 * 따라서 controller class를 Form/Save/List로 나누는 대신 하나의 class로 통합할 수 있다.
 *
 * 이 경우 각 메소드의 RequestMapping 경로 중 /springmvc/v2/members가 중복되는데,
 * class에 @Requestmapping("/springmvc/v2/members")를 추가함으로써 해당 중복을 제거하는 것이 가능하다.
 * 즉 RequestMapping 경로: (Class의 mapping 정보) + (Method의 mapping 정보)
 */
@Controller
@RequestMapping("/springmvc/v2/members")
public class SpringMemberControllerV2 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @RequestMapping("/new-form")  // /springmvc/v2/members/new-form (by RequestMapping annotation): 해당 url이 호출된 경우
    public ModelAndView newForm() {
        return new ModelAndView("new-form");  // /WEB-INF/views/new-form.jsp (by view resolver): 해당 jsp 파일을 불러옴
    }

    @RequestMapping("/save")  //  /springmvc/v2/members/save
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response) {

        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        ModelAndView mv = new ModelAndView("save-result");
        mv.addObject("member", member);
        return mv;
    }

    @RequestMapping  // /springmvc/v2/members
    public ModelAndView members() {

        List<Member> members = memberRepository.findAll();

        ModelAndView mv = new ModelAndView("members");
        mv.addObject("members", members);

        return mv;
    }
}
