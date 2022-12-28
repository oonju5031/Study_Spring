package hello.springMVC1_springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class MappingController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 다중 url 매핑
     * 배열을 이용해 여러 url을 매핑할 수 있다.
     */
    @RequestMapping({"/hello-basic", "/hello-basic2"})
    public String helloBasic() {
        log.info("helloBasic");
        return "hello";
    }

    /**
     * Request method 제한
     * RequestMapping annotation의 value속성을 이용해 HTTP method를 제한할 수 있다.
     * (GET, HEAD, POST, PUT, PATCH, DELETE)
     */
    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetV1() {
        log.info("mappingGetV1");
        return "ok";
    }

    /**
     * Request method의 축약
     * RequestMapping annotation의 method 제한을 다음 annotation으로 축약할 수 있다.
     * (@GetMapping, @HeadMapping, @PostMapping, @PutMapping, @PatchMapping, @DeleteMapping)
     */
    @GetMapping("/mapping-get-v2")  // @RequestMapping의 method 제한을 축약할 수 있다.
    public String mappingGetV2() {
        log.info("mappingGetV2");
        return "ok";
    }

    /**
     * PathVariable(경로 변수)의 매핑
     * 변수명이 같으면 중간을 생략할 수 있다.
     * 예: @PathVariable("userId") String userId -> @PathVariable String userId
     */
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String data) {
        log.info("mappingPath userId = {}", data);
        return "ok";
    }

    /**
     * PathVariable의 다중 매핑
     */
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable String orderId) {
        log.info("mappingPath userId = {}, orderId = {}", userId, orderId);
        return "ok";
    }

    /**
     * 특정 파라미터 조건을 이용한 매핑
     * Query parameter가 해당 조건을 만족하여야만 호출된다. 예시는 다음과 같다.
     * params = "mode"        /mapping-param?mode=* 에서 호출됨
     * params = "!mode"        /mapping-param?mode=* 에서 호출되지 않음
     * params = "mode=debug"        /mapping-param?mode=debug 에서 호출됨
     * params = "mode!=debug"        /mapping-param?mode=debug 에서 호출되지 않음
     * params = {"mode=debug", "data=good"}        /mapping-param?mode=debug&data=good 에서 호출됨
     * (mode = debug와 같이 whitespace가 있으면 안 된다!)
     */
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParam() {
        log.info("mappingParam");
        return "ok";
    }

    /**
     * 특정 헤더 조건을 이용한 매핑
     * 파라미터 조건을 이용한 매핑과 유사하다.
     * HTTP request의 header가 해당 조건을 만족하여야만 호출된다. 예시는 다음과 같다.
     * headers = "mode"        요청에 mode를 key로 하는 header가 있어야 호출됨
     * headers = "!mode"
     * headers = "mode=debug"        요청에 mode를 key로 하고 debug를 value로 하는 header가 있어야 호출됨
     * headers = "mode!= debug"
     * (mode = debug와 같이 whitespace가 있으면 안 된다!)
     */
    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader() {
        log.info("mappingHeader");
        return "ok";
    }

    /**
     * 특정 미디어 타입 조건을 이용한 매핑(Content-Type)
     * HTTP request의 Content-Type header가 해당 조건을 만족하여야만 호출된다. 만족하지 않는 경우 HTTP 415 상태코드(Unsupported Media Type)를 반환한다.
     * 상기한 헤더 조건 매핑(headers = "Content-Type = application/json")도 가능하지만, consumes는 부가적인 기능을 제공하기에 이를 사용하는 것이 권장된다. 예시는 다음과 같다.
     * consumes = "application/json"        header의 Content-Type가 application/json어야 호출됨
     * consumes = "!application/json"
     * consumes = "application/*"
     * consumes = {"text/plain", "application/*}
     * consumes = "*\/*"
     * consumes = MediaType.APPLICATION_JSON_VALUE
     */
    @PostMapping(value = "/mapping-consume", consumes = "application/json")
    public String mappingConsumes() {
        log.info("mappingConsumes");
        return "ok";
    }

    /**
     * 특정 미디어 타입 조건을 이용한 매핑(Accept)
     * HTTP request의 Accept header가 해당 조건을 만족하여야만 호출된다. 만족하지 않는 경우 HTTP 406 상태코드(Not Acceptable)을 반환한다.
     * 상기한 헤더 조건 매핑도 가능하지만, 역시 부가적인 기능을 사용하기에 produces를 사용하는 것이 권장된다. 예시는 다음과 같다.
     * produces = "text/html"
     * produces = "!text/html"
     * produces = "text/*"
     * produces = {"text/plain", "application/*"}
     * produces = "*\/*"
     * produces = MediaType.TEXT_PLAIN_VALUE
     * produces = "text/plain;charset=UTF-8"
     */
    @PostMapping(value = "/mapping-produce", produces = "text/html")
    public String mappingProduces() {
        log.info("mappingProduces");
        return "ok";
    }
}
