package hello.springMVC1_springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springMVC1_springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * HTTP 요청 메시지 조회: HTTP message body에 담긴 데이터
 * 클라이언트에서 서버로 요청 데이터를 전달할 때 쓰는 방식 3가지(Query parameter, HTML form, HTTP message body에 데이터 입력하여 전송) 중 세 번째를 처리하는 Controller아며,
 * 개중 HTTP message body가 JSON 형식인 경우이다.
 * <p>
 * {"username": "JunYoungLee", "age" : "24"}, Content-Type: application/json
 */
@Slf4j
@Controller
public class RequestBodyJsonController {

    private ObjectMapper objectMapper = new ObjectMapper();  // Jackson

    /**
     * Spring을 사용하지 않고 Servlet을 통해 JSON을 입력받아 Jackson으로 가공
     */
    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody = {}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());

        response.getWriter().write("ok");
    }
}
