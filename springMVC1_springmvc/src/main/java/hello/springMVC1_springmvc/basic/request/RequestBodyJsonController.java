package hello.springMVC1_springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springMVC1_springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * HTTP 요청 메시지: HTTP message body에 담긴 데이터
 * 클라이언트에서 서버로 요청 데이터를 전달할 때 쓰는 방식 3가지(Query parameter, HTML form, HTTP message body에 데이터 입력하여 전송) 중 세 번째를 처리하는 Controller아며,
 * 개중 HTTP message body가 JSON 형식인 경우이다.
 * <p>
 * {"username": "JunYoungLee", "age" : "24"}, Content-Type: application/json
 */
@Slf4j
@Controller
public class RequestBodyJsonController {

    private final ObjectMapper objectMapper = new ObjectMapper();  // Jackson

    /**
     * V1: Spring을 사용하지 않고 Servlet을 통해 JSON을 입력받아 Jackson으로 가공
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

    /**
     * V2: @RequestBody, @ResponseBody를 통해 HTTP message body의 정보를 편리하게 조회할 수 있다.
     * 만일 헤더 정보가 필요한 경우 HttpEntity나 @RequestHeader를 사용하면 된다.
     * (=RequestBodyStringController.java의 V4)
     */
    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {

        log.info("messageBody = {}", messageBody);  // raw한 String 형식의 JSON
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);  // String 형식의 JSON을 Jackson을 통해 HelloData 객체로 변환
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());  // HelloData 객체이므로 key값을 통해 읽을 수 있다!

        return "ok";
    }

    /**
     * V3: @RequestBody로 직접 만든 객체(HelloData)를 지정하는 것도 가능하다.
     * 이 경우 HTTP message converter가 HTTP message body의 내용을 문자 또는 객체로 변환한다. 즉, 상기 V2에서 직접 수행했던 Jackson을 통해 객체로 변환하는 작업을 대신 처리해준다.
     * (HTTP message body에 있는 content-type을 기반으로 JSON임을 인식한다.)
     *
     * 이 때 @RequestBody를 생략하면 request parameter를 처리하는 @ModelAttribute로 인식하므로 해당 annotation은 생략할 수 없다.
     * 만일 생략하는 경우 request parameter가 없이 request body만 있으므로 username과 age에는 각각 기본값인 null, 0이 입력된다.
     */
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData helloData) {

        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    /**
     * V4: @RequestBody 대신 HttpEntity를 사용하여 직접 만든 객체(HelloData)를 지정하는 것도 가능하다.
     */
    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> data) {

        HelloData helloData = data.getBody();  // Http 정보 중 body만을 가져온다.
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    /**
     * V5: @ResponseBody는 입력값뿐만 아니라 반환값에도 적용할 수 있다.
     * 즉, 다음과 같이 HelloData를 반환할 수 있다. 이 경우 출력 시에는 HttpMessageConverter에 의해 객체가 JSON 문자 형식으로 변환된다.
     *
     * * @RequestBody 요청 시: JSON 요청이 HTTP message converter에 의해 객체로 변환
     * * @RequestBody 응답 시: 객체가 HTTP message converter에 의해 JSON 응답으로 변환(client의 accept정보를 기반으로 변환할 객체를 지정)
     *
     * V3-V4와 같이 HttpEntity를 사용하는 것 또한 가능하다.
     */
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData helloData) {

        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());

        return helloData;
    }
}
