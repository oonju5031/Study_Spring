package hello.servlet.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.HelloData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
응답 메시지는 크게 세 가지로 나뉜다.
1. 단순 텍스트 보내기
2. HTML 보내기
3. HTML message body에 json 보내기
 */
@WebServlet(name = "responseJsonServlet", urlPatterns = "/response-json")
public class ResponseJsonServlet extends HttpServlet {

    // ObjectMapper: 객체를 문자열(json)로 변환
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Content-Type: application/json
        response.setContentType("application.json");
        response.setCharacterEncoding("utf-8");

        HelloData helloData = new HelloData();
        helloData.setUsername("Lee");
        helloData.setAge(24);

        // {"username":"Lee", "age":24}
        String result = objectMapper.writeValueAsString(helloData);
        response.getWriter().write(result);
    }
}
