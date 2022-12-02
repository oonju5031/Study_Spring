package hello.servlet.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "helloServlet", urlPatterns = "/hello")  // 이름은 임의로 작성 가능
public class HelloServlet extends HttpServlet {

    /*
     Servlet이 호출되면 해당 메소드도 호출된다.
     HttpServletRequest request: 서블릿 컨테이너가 HTTP 요청 객체를 만들어 서버에 전송한다.
     HttpServletResponse response: 서버에서 전송된 HTTP 응답 객체가 저장된다.
    */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("HelloServlet.service");
        System.out.println("request = " + request);
        System.out.println("response = " + response);

        /*
         Query의 parameter를 request.getParameter()를 이용하여 손쉽게 읽을 수 있다.
         ex) localhost:8081/hello?username=lee 입력 시 String username = "lee"
        */
        String username = request.getParameter("username");
        System.out.println("username = " + username);

        response.setContentType("text/plain"); // 응답 헤더의 Content-Type: 단순 문자
        response.setCharacterEncoding("utf-8");  // 응답 헤더의 Content-Type: 문자 인코딩
        response.getWriter().write("hello " + username);  // 웹 페이지에 출력
    }
}
