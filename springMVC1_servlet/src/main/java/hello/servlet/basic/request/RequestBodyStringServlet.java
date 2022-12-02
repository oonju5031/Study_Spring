package hello.servlet.basic.request;

import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * HTTP 요청의 데이터를 조회하는 메소드:
 * API 메시지 바디(단순 텍스트)
 */
@WebServlet(name = "requestBodyStringServlet", urlPatterns = "/request-body-string")
public class RequestBodyStringServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream inputStream = request.getInputStream();  // message body의 내용을 byte code로 얻는다.
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);// Spring이 제공하는 utility를 통해 byte code를 string code로 변환, byte code의 encoding을 명시해 주어야 함

        System.out.println("messageBody = " + messageBody);

        // 웹 페이지 표시
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        /*
        TODO: Q. 위 인코딩이 이거랑 무슨 차이지?
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
         */
        response.getWriter().write("HTTP 요청 데이터: API 메시지 바디(단순 텍스트)");
    }
}
