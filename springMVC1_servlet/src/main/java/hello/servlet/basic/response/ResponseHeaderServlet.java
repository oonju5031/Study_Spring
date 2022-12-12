package hello.servlet.basic.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // [status-line]
        response.setStatus(HttpServletResponse.SC_OK);  // = 200 (HttpServletResponse에 정의된 상수를 사용하길 권장함)
        /*response.setStatus(HttpServletResponse.SC_BAD_REQUEST);  // 400 bad request*/

        // [response-headers]
        response.setHeader("Content-Type", "text/plain;charset=utf-8");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");  // 캐시 무효화
        response.setHeader("Pragma", "no-cache");  // 캐시 무효화(이전 버전)
        response.setHeader("my-header", "hello");  // 임의의 헤더를 추가할 수 있음

        // [header 편의 메소드]
        content(response);  // 상기 headers를 대체할 수 있음
        cookie(response);
        redirect(response);

        // [message body]
        PrintWriter writer = response.getWriter();
        writer.println("확인");
    }

    private void content(HttpServletResponse response) {
        // Content-Type: text/plain;charset=utf-8
        // Content-Length: 2
        // response.setHeader("Content-Type", "text/plain;charset=utf-8);
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        // response.setContentLength(2);  // 생략 시 자동으로 생성됨
    }

    private void cookie(HttpServletResponse response) {
        // Set-Cookie: myCookie = good; Max-Age = 600;
        // response.setHeader("Set-Cookie", "myCookie=good; Max-Age = 600");
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600);  // 600초 동안 지속
        response.addCookie(cookie);
    }

    private void redirect(HttpServletResponse response) throws IOException {
        // Status Code 302
        // Location: /basic/hello-form.html

        // response.setStatus(HttpServletResponse.SC_FOUND);  // 302
        // response.setHeader("Location", "/basic/hello-form.html");
        response.sendRedirect("/basic/hello-form.html");
    }
}
