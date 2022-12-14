package hello.servlet.web.frontcontroller.v2;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.ControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * MVC Pattern with FrontController V2:
 * controller로의 URL 요청을 받아 Mapping하며,
 * controller에서 view로의 요청도 수행하는 FrontController
 */
@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet {

    // 요청 매핑 정보를 저장하는 HashMap: {key URL : value ControllerV2를 구현한 클래스}
    private Map<String, ControllerV2> controllerMap = new HashMap<>(); // key: url, value: ControllerV1

    // 생성자에 요청 매핑 정보 입력: 해당 servlet이 생성될 때 HashMap에 값을 저장함
    public FrontControllerServletV2() {
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();  // URI: /front-controller/v2/(하위)

        ControllerV2 controller = controllerMap.get(requestURI);  // 반환한 URI로 value를 찾는다.

        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);  // 없는 URL인 경우 404 반환
            return;  // service() 종료
        }

        // MyView.java에서 render 수행
        MyView view = controller.process(request, response);
        view.render(request, response);
    }
}
