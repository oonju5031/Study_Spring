package hello.springMVC1_springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * HTTP 요청: 기본 및 헤더 정보 조회
 */
@Slf4j
@RestController
public class RequestHeaderController {

    /**
     * Spring의 annotation 기반 컨트롤러는 다양한 파라미터를 지원한다.
     * @param request 요청 정보
     * @param response 응답 정보
     * @param httpMethod HTTP method(GET, POST, PUT, PATCH, DELETE, ...)
     * @param locale 언어 정보
     * @param headerMap 헤더 정보(MultiValueMap: 중복 key값 지원)
     * @param host 헤더 정보 중 필수인 host
     * @param cookie 쿠키 정보(value: 쿠키 이름, required: 쿠키의 필수 여부)
     * @return String
     */
    @RequestMapping("/headers")
    public String headers(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpMethod httpMethod,
                          Locale locale,
                          @RequestHeader MultiValueMap<String, String> headerMap,
                          @RequestHeader("host") String host,
                          @CookieValue(value = "myCookie", required = false) String cookie
    ) {
        // Logger
        log.info("request = {}", request);
        log.info("response = {}", response);
        log.info("httpMethod = {}", httpMethod);
        log.info("locale = {}", locale);
        log.info("headerMap = {}", headerMap);
        log.info("host = {}", host);
        log.info("cookie = {}", cookie);

        return "ok";
    }
}
