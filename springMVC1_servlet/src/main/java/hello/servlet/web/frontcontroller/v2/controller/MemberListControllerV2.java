package hello.servlet.web.frontcontroller.v2.controller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.ControllerV2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * MVC Pattern with FrontController V2:
 * 해당 controller로의 호출, 해당 컨트롤러가 수행하는 view 호출 둘 다 FrontController에서 담당
 * model에서 값을 불러오는 로직만을 가진 controller
 */
public class MemberListControllerV2 implements ControllerV2 {

    MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Member> members = memberRepository.findAll();
        request.setAttribute("members", members);  // setAttribute(property, field)

        return new MyView("/WEB-INF/views/members.jsp");
    }
}
