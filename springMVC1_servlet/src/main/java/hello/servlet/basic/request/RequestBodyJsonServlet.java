package hello.servlet.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.HelloData;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "requestBodyJsonServlet", urlPatterns = "/request-body-json")
public class RequestBodyJsonServlet extends HttpServlet {

    // Jackson: 직렬화(객체 -> 문자열), 역직렬화(문자열 -> 객체)를 보조하는 Spring Boot 기본 라이브러리
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        System.out.println("messageBody = " + messageBody);  // raw한 JSON이 출력된다: Jackson을 통해 parsing해야 함

        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);  // JSON(문자열)을 HelloData(객체)으로 역직렬화
        System.out.println("username = " + helloData.getUsername());
        System.out.println("age = " + helloData.getAge());

        // 웹 페이지 표시
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("HTTP 요청 데이터: API 메시지 바디(단순 텍스트)");
    }
}
