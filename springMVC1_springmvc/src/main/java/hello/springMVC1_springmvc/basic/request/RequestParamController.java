package hello.springMVC1_springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * HTTP 요청 파라미터 조회: Query Parameter, HTML Form
 * 클라이언트에서 서버로 요청 데이터를 전달할 때 쓰는 방식 3가지(Query parameter, HTML form, HTTP message body에 데이터 입력하여 전송) 중 앞의 두 가지를 처리하는 Controller이다.
 * (GET query parameter, POST HTML Form 둘 다 형식이 같으므로 동일한 로직으로 조회할 수 있다.)
 */
@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("username = {}, age = {}", username, age);

        response.getWriter().write("ok");
    }
}

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
