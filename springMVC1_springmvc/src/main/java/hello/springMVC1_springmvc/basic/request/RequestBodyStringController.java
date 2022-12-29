package hello.springMVC1_springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

/**
 * HTTP 요청 메시지 조회: HTTP message body에 담긴 데이터
 * 클라이언트에서 서버로 요청 데이터를 전달할 때 쓰는 방식 3가지(Query parameter, HTML form, HTTP message body에 데이터 입력하여 전송) 중 세 번째를 처리하는 Controller이다.
 */
/*
클라이언트에서 서버로 요청 데이터를 전달할 때는 주로 다음 3가지 방식을 사용한다.
1. (GET) query parameter 사용
     /url?username=hello&age=20
2. (POST) HTML Form 사용
     Content-Type: application/x-www-form-urlencoded
     message body에 query parameter 형식으로 전달(username=hello&age=20)
3. (POST/PUT/PATCH) HTTP message body에 데이터를 담아 전송
     HTTP API에서 주로 사용(JSON, XML, text)
해당 클래스는 개중 3에 대해 다룬다.
 */
@Slf4j
@Controller
public class RequestBodyStringController {

    /**
     * V1: Servlet을 통해 Raw한 text를 조회
     */
    @PostMapping("/request-body-string-v1")
    public void requestBodyStringV1(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody = {}", messageBody);

        response.getWriter().write("ok");
    }

    /**
     * V2: 위의 requestBodyString()에서 Servlet 전체가 필요한 것이 아니므로
     * 이를 Spring에서 지원하는 파라미터를 통해 InputStream, OutputStream으로 축약할 수 있다.
     * <p>
     * InputStream(Reader): HTTP request message의 body 내용을 직접 조회
     * OutputStream(Writer): HTTP response message의 body에 직접 결과 출력
     */
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {

        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody = {}", messageBody);

        responseWriter.write("ok");
    }

    /**
     * V3: HttpEntity는 Spring에서 제공하는 parameter이며, HttpMessageConverter를 통해 HTTP header, body의 메시지 바디 정보를 직접 조회할 수 있도록 돕는다.
     * 이는 응답에도 사용할 수 있다. 이 경우 메시지 바디 정보를 직접 반환하며, 헤더 정보를 포함할 수 있다.
     * <p>
     * (@Requestparam, @ModelAttribute와 같은 요청 파라미터 조회와는 무관하다.)
     * (View를 사용하지 않는다.)
     */
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) {

        String body = httpEntity.getBody();

        log.info("messageBody = {}", body);

        return new HttpEntity<>("ok");
    }

    /**
     * V3-1: HttpEntity를 상속한 RequestEntity와 ResponseEntity는 보다 확장된 기능을 제공한다.
     * <p>
     * RequestEntity: url 정보를 추가
     * ResponseEntity: HTTP 상태 코드 설정 가능
     */
    @PostMapping("/request-body-string-v3-1")
    public HttpEntity<String> requestBodyStringV3_1(RequestEntity<String> httpEntity) {

        String body = httpEntity.getBody();

        log.info("messageBody = {}", body);

        return new ResponseEntity<>("ok", HttpStatus.CREATED);
    }

    /**
     * V4: @RequestBody, @ResponseBody를 통해 HTTP message body의 정보를 편리하게 조회할 수 있다.
     * 만일 헤더 정보가 필요한 경우 상술한 HttpEntity를 사용하거나 @RequestHeader를 사용하면 된다.
     * <p>
     * (V3과 마찬가지로 View를 사용하지 않는다.)
     */
    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) {

        log.info("messageBody = {}", messageBody);

        return "ok";
    }
}
