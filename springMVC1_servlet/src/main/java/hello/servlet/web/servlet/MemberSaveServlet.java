package hello.servlet.web.servlet;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet으로 회원 관리 웹 어플리케이션 만들기:
 * MemberFormServlet에서 입력한 정보를 전달받아 저장하는 페이지
 */
@WebServlet(name = "memberSaveServlet", urlPatterns = "/servlet/members/save")
public class MemberSaveServlet extends HttpServlet {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("MemberSaveServlet.service");

        // request의 form값을 읽음: GET query string, HTML POST form 둘 다 getParameter() 사용
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));// getParameter()는 항상 string이므로 int로 변환하여야 함

        // 읽은 정보를 MemberRepository에 저장
        Member member = new Member(username, age);
        memberRepository.save(member);

        // HTML 코드로 저장한 내용을 동적으로 출력(동적: 변수/메소드 사용 가능)
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter w = response.getWriter();
        w.write("<html>\n" +
                "<head>\n" +
                "   <meta charset=\"UTF-8\">\n" +
                "</head>\n" +
                "<body>\n" +
                "성공\n" +
                "<ul>\n" +
                "   <li>id="+member.getId()+"</li>\n" +
                "   <li>username="+member.getUsername()+"</li>\n" +
                "   <li>age="+member.getAge()+"</li>\n" +
                "</ul>\n" +
                "<a href=\"/index.html\">메인</a>\n" +
                "</body>\n" +
                "</html>");
    }
}
