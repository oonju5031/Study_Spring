package hello.springMVC1_springmvc.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/*
@Controller: 반환되는 String이 view name으로 인식된다.
@RestController: 반환되는 String은 그대로 String으로 HTTP 메시지 바디에 입력된다.
        - body 영역에 문자열 "ok"만 입력된 것을 확인할 수 있다.
        - REST API(Representational State Transfer API)의 기능이다.
*/
@RestController
public class LogTestController {

    private final Logger log = LoggerFactory.getLogger(getClass());  // Lombok에서 제공하는 anntotation @Slf4j로 대체할 수 있다.
    // getClass(): 객체가 현재 참조하고 있는 클래스 정보를 반환한다. (== LogTestController.class)

    @RequestMapping("/log-test")
    public String logTest() {

        String name = "Test Message";

        System.out.println("name = " + name);

        log.trace("trace log = {}", name);  // 로컬
        log.debug("debug log = {}", name);  // 개발 서버
        log.info(" info log = {}", name);  // 운영 서버
        log.warn(" warn log = {}", name);
        log.error("error log = {}", name);

        return "ok";
        /*
        로그는 System.out과 비교하여 다음과 같은 장점을 가진다.
        1. 여러 부가 정보(thread 정보, class 이름)를 함께 볼 수 있고, 출력 모양을 조절할 수 있다.
        2. Log level에 따라 출력하는 로그를 조절할 수 있다.
        3. console 외에도 설정에 따라 파일, 네트워크 등 별도의 위치에 로그를 남길 수 있다.
        4. 내부 버퍼링, 멀티 쓰레드와 같은 성능적 측면에서 우위를 가진다.

        Log level은 하위부터 상위까지 TRACE, DEBUG, INFO, WARN, ERROR가 있다.
        기본 로그 레벨은 INFO이며, 이는 INFO 및 상위 수준 레벨(INFO, WARN, ERROR)이 로그에 기록됨을 의미한다.
        로그 레벨은 application.property에서 설정할 수 있다.

        {}가 변수인 name으로 치환되어 'trace log = Test Message' 와 같이 출력된다. 이 때,
        log.debug("data = " + data)  :  로그 출력 레벨이 info인 경우에도 "+" 연산이 수행된다. 때문에 필요하지 않은 메모리의 낭비가 생긴다.
        log.debug("data = {}", data) :  로그 출력 레벨이 info인 경우 parameter의 대입이 수행되지 않는다. 때문에 메모리의 낭비가 생기지 않는다.
        상기한 Java의 동작 구조(method 실행 전 "+" 연산 수행/method 실행 후 parameter 대입 수행)상 "+"를 이용한 로깅은 지양하여야 한다.

        로그의 결과는 다음과 같은 형식으로 나온다.
        2022-12-28 11:05:47.750  INFO 1100 --- [nio-8081-exec-6] h.s.basic.LogTestController  :  info log=slf4j test
        이는 차례대로 다음 정보를 나타낸다.
        [현재 날짜] [현재 시각] [로그 레벨] [프로세스 ID] --- [현재 실행한 Thread] [파일 경로] : [메시지]
         */
    }
}
