package hello.springMVC1_springmvc.basic.response;

import hello.springMVC1_springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * HTTP 응답: HTTP API, message body에 직접 입력
 *
 * HTTP API를 제공하는 경우에는 HTML이 아닌 데이터를 전달해야 하므로, HTTP message body에 JSON과 같은 형식으로 데이터를 담아 전송한다.
 *
 * 정적 리소스(HTML 등)나 view template를 사용하는 경우에도 결국엔 HTTP request message body에 HTML 데이터가 담겨서 전송되나,
 * 해당 클래스는 정적 리소스나 view template를 사용하지 않고 직접 HTTP request message를 전달하는 경우를 다룬다.
 *
 * 추가: @ResponseBody를 클래스 레벨에 사용하면 해당 클래스의 모든 메소드들이 해당 annotation을 가진 것과 동일하게 작동한다.
 * 이는 다시 @RestController로 축약할 수 있는데, 해당 annotation은 @Controller + @ResponseBody와 동일하다.
 * 뷰 템플릿을 사용하지 않고 HTTP 메시지 바디에 직접 데이터를 입력하기 때문에 Rest API(HTTP API)를 만들 때 사용한다.
 */
/*
Spring server에서 응답 데이터를 만드는 방법은 다음 세 가지가 있다.
1. 정적 리소스
    웹 브라우저에 정적인 HTML, css, js를 제공할 때 사용한다.
2. 뷰 템플릿 사용
    동적인 HTML을 제공할 때 사용한다.
3. HTTP 메시지 사용
    HTTP API를 제공하는 경우엔 HTML이 아니라 데이터를 전달하여야 하므로, HTTP message body에 JSON, xml과 같은 데이터를 넣어 전송한다.
 */
@Slf4j
@Controller
// @ResponseBody
// @RestController
public class ResponseBodyController {

    /**
     * 단순 텍스트 V1: 기본 기능
     */
    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("ok");
    }

    /**
     * 단순 텍스트 V2: Spring에서 제공하는 ResponseEntity
     */
    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() {
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    /**
     * 단순 텍스트 V3: Spring에서 제공하는 ResponseBody annotation
     */
    @ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3() {
        return "ok";
    }

    /**
     * JSON V1: ResponseEntity
     */
    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1() {
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);

        return new ResponseEntity<>(helloData, HttpStatus.OK);
    }

    /**
     * JSON V2: ResponseBody
     *
     * Annotation @ResponseStatus: v1과 동일하게 HTTP 상태 메시지를 반환하기 위해 사용한다.
     */
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2() {
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);

        return helloData;
    }

}
