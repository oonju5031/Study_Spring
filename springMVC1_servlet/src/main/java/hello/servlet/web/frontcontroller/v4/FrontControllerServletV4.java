package hello.servlet.web.frontcontroller.v4;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * MVC Pattern with FrontController V4:
 * controller로의 URL 요청을 받아 Mapping하며,
 * controller에서 view로의 요청도 수행하는 FrontController
 * controller가 servlet에 종속적이지 않도록 ModelView 분리
 * model 객체를 controller에서가 아닌 FrontController에서 생성하도록 리팩토링
 */
@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {

    // 요청 매핑 정보를 저장하는 HashMap: {key URL : value ControllerV4를 구현한 클래스}
    private Map<String, ControllerV4> controllerMap = new HashMap<>(); // key: url, value: ControllerV4

    // 생성자에 요청 매핑 정보 입력: 해당 servlet이 생성될 때 HashMap에 값을 저장함
    public FrontControllerServletV4() {
        controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();  // URI: /front-controller/v4/(하위)

        ControllerV4 controller = controllerMap.get(requestURI);  // 반환한 URI로 value를 찾는다.

        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);  // 없는 URL인 경우 404 반환
            return;  // service() 종료
        }

        // V4: Controller의 인자 model 추가
        Map<String, String> paramMap = createParamMap(request);
        Map<String, Object> model = new HashMap<>();

        String viewName = controller.process(paramMap, model);

        MyView view = viewResolver(viewName);

        // Render: ModelView에서 model을 꺼내는 것이 아닌, FrontController에서 model을 생성하여 사용하는 방식으로 변경
        view.render(model, request, response);
    }

    /*
    HttpServletRequset의 모든 parameter를 가져와 {이름, 파라미터 값}을 paramMap에 저장 후 반환하는 메소드
    (Logic의 level을 맞추기 위해 별도의 메소드로 추출)
    */
    private Map<String, String> createParamMap(HttpServletRequest request) {

        Map<String, String> paramMap = new HashMap<>();

        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));

        return paramMap;
    }

    /*
    논리 이름을 물리적인 위치 이름으로 바꾸는 메소드
    (Logic의 level을 맞추기 위해 별도의 메소드로 추출)
     */
    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");  // 물리 위치 이름(예: /WEB-INF/views/new-form.jsp)
    }


}
