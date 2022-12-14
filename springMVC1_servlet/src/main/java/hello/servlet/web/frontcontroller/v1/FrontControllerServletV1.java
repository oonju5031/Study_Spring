package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// /front-controller/v1/* : v1 하위의 url이 호출되면 이 servlet이 호출됨
@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {

    // 매핑 정보를 저장하는 HashMap: {URL, ControllerV1을 구현한 클래스}
    private Map<String, ControllerV1> controllerMap = new HashMap<>(); // key: url, value: ControllerV1

    // 생성자에 매핑 정보 입력: 해당 servlet이 생성될 때 HashMap에 값을 저장함
    public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV1.service");

        String requestURI = request.getRequestURI();  // URI를 root부터 반환한다. ex) https://localhost.8081/front-controller/v1/members -> /front-controller/v1/members 반환

        ControllerV1 controller = controllerMap.get(requestURI);  // 반환한 URI로 value를 찾는다.

        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);  // 없는 URL인 경우 404 반환
            return;  // service() 종료
        }

        controller.process(request, response);
    }
}
