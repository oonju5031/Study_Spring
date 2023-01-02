package hello.springMVC1_springmvc.basic.request;

import hello.springMVC1_springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * HTTP 요청 메시지: 파라미터(Query Parameter, HTML Form)
 * 클라이언트에서 서버로 요청 데이터를 전달할 때 쓰는 방식 3가지(Query parameter, HTML form, HTTP message body에 데이터 입력하여 전송) 중 앞의 두 가지를 처리하는 Controller이다.
 * (GET query parameter, POST HTML Form 둘 다 형식이 같으므로 동일한 로직으로 조회할 수 있다.)
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
해당 클래스는 개중 1, 2에 대해 다룬다.
 */
@Slf4j
@Controller
public class RequestParamController {

    /**
     * V1: HttpServletRequest가 제공하는 방식으로 요청 파라미터 조회
     */
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("username = {}, age = {}", username, age);

        response.getWriter().write("ok");
    }

    /**
     * V2: Spring이 제공하는 @RequestParam을 사용하여 요청 파라미터 조회
     *
     * @param memberName username
     * @param memberAge  age
     * @return @ResponseBody에 의해 return값이 String 그대로 response body에 입력된다(=@RestController)
     */
    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(@RequestParam("username") String memberName,
                                 @RequestParam("age") int memberAge) {

        log.info("username = {}, age = {}", memberName, memberAge);

        return "ok";
    }


    /**
     * V3: V2 + 변수명을 query parameter와 동일하게 변경함으로써 @RequestParam의 name속성 생략
     */
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(@RequestParam String username,
                                 @RequestParam int age) {

        log.info("username = {}, age = {}", username, age);

        return "ok";
    }

    /**
     * V4: V3 + 변수명이 query parameter와 동일하면 String, int, Integer 등의 단순 타입은 @RequestParam 자체를 생략 가능
     * 하지만 annotation을 완전히 생략하는 것은 코드 가독성 측면에서 좋지 않다는 의견도 있다.
     */
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {

        log.info("username = {}, age = {}", username, age);

        return "ok";
    }

    /**
     * RequestParam annotation의 required 속성: 기본값은 true이며, 해당 query parameter가 없으면 오류가 출력된다.
     *
     * @param username required = false: 값이 null이어도 오류가 발생하지 않음
     * @param age      required = false: 값이 null이어도 오류가 발생하지 않음
     *                 <p>
     *                 null과 ""는 동일하지 않음: username의 required가 true일 때 /request-param-required?username= 를 입력하여도 오류가 출력되지 않음(username = "")
     *                 기본형 변수 int는 null값을 가질 수 없음: 참조형 변수 Integer를 사용하거나 후술할 defaultValue 속성을 사용해야 함
     */
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(@RequestParam(required = false) String username,
                                       @RequestParam(required = false) Integer age) {

        log.info("username = {}, age = {}", username, age);

        return "ok";
    }

    /**
     * RequestParam annotation의 defaultValue 속성: 해당 parameter의 값이 null이거나 빈 문자("")인 경우 defaultValue를 대신 입력한다.
     *
     * @param username 값이 null인 경우 defaultValue인 guest가 입력됨 (?username=guest)
     * @param age      값이 null인 경우 defaultValue인 -1이 입력됨 (?age=-1)
     *                 <p>
     *                 request의 parameter 값이 null이어도 defaultValue가 입력되어 null이 아니게 되므로 required 속성은 필요하지 않음
     *                 마찬가지로 null이 입력되는 경우가 없으므로 참조형 Integer 대신 int를 사용하여도 무방함
     *                 <p>
     *                 null뿐만 아니라 빈 문자(예: ?username=)인 경우에도 defaultValue가 입력되는 점에 주의
     */
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(@RequestParam(defaultValue = "guest") String username,
                                      @RequestParam(defaultValue = "-1") int age) {

        log.info("username = {}, age = {}", username, age);

        return "ok";
    }

    /**
     * Map을 이용한 request parameter 조회
     *
     * @param paramMap Map(파라미터의 값이 확실히 한 개) 또는 MultiVauleMap(파라미터의 값이 여러 개)으로 조회할 수 있다.
     *                 <p>
     *                 Map(key=value)
     *                 MultiMap(key=[value1, value2, ...])  예: key=userIds, value=[id1, id2, id3]
     */
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {

        log.info("username = {}, age = {}", paramMap.get("username"), paramMap.get("age"));

        return "ok";
    }

    /**
     * 읽은 request parameter 값을 Spring에서 제공하는 @ModelAttribute를 이용하여 객체에 주입하기
     * <p>
     * Spring은 @ModelAttribute에 대해 다음 작업을 한다.
     * 1. HelloData 객체를 생성한다.
     * 2. 요청 파라미터 이름으로 HelloData 객체의 property를 찾는다. 그리고 해당 property의 setter를 호출하여 parameter의 값을 입력(binding)한다.
     * ex) 파라미터 이름이 username이면 setUsername() 호출하여 값 입력
     * <p>
     * Property란:
     * 객체 내에 입력자 getUsername(), 수정자 setUsername()이 있으면 해당 객체는 username이라는 property를 가진다(get/set을 제거하고 앞자를 소문자로 변환하여 username이 된다)
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
    /*
    public String modelAttributeV1(@RequestParam String username, @RequestParam int age) {
        HelloData helloData = new HelloData();
        helloData.setUsername(username);
        helloData.setAge(age);
     와 유사하다.
     */

        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
        log.info("helloData = {}", helloData.toString());

        return "ok";
    }

    /**
     * Annotation @ModelAttribute는 생략 가능하다.
     * <p>
     * Spring은 상술한 바와 같이 단순 타입(String, int, integer)인 경우 @RequestParam를 생략한 것으로,
     * 나머지의 경우(Argument resolver로 지정한 타입 제외)는 @ModelAttribute를 생략한 것으로 판단한다.
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {

        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
        log.info("helloData = {}", helloData.toString());

        return "ok";
    }
}
