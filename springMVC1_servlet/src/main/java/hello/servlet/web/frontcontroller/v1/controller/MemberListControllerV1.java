package hello.servlet.web.frontcontroller.v1.controller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.v1.ControllerV1;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * MVC Pattern with FrontController V1:
 * model(memberRepository)에 저장된 모든 member들을
 * view(members.jsp)를 통해 출력하는 controller
 * 해당 controller의 url 요청은 frontcontroller가 담당한다.
 */
public class MemberListControllerV1 implements ControllerV1 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 로직은 Servlet MVC와 동일

        List<Member> members = memberRepository.findAll();
        request.setAttribute("members", members);  // setAttribute(property, field)

        // view 호출
        String viewPath = "/WEB-INF/views/members.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }
}
