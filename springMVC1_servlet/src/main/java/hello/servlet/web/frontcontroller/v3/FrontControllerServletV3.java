package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.ControllerV2;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * MVC Pattern with FrontController V3:
 * controller로의 URL 요청을 받아 Mapping하며,
 * controller에서 view로의 요청도 수행하는 FrontController
 * controller가 servlet에 종속적이지 않도록 ModelView 분리
 */
@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    // 요청 매핑 정보를 저장하는 HashMap: {key URL : value ControllerV3를 구현한 클래스}
    private Map<String, ControllerV3> controllerMap = new HashMap<>(); // key: url, value: ControllerV3

    // 생성자에 요청 매핑 정보 입력: 해당 servlet이 생성될 때 HashMap에 값을 저장함
    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();  // URI: /front-controller/v3/(하위)

        ControllerV3 controller = controllerMap.get(requestURI);  // 반환한 URI로 value를 찾는다.

        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);  // 없는 URL인 경우 404 반환
            return;  // service() 종료
        }

        Map<String, String> paramMap = createParamMap(request);
        ModelView mv = controller.process(paramMap);

        String viewName = mv.getViewName(); // 논리 이름(예: new-form)
        MyView view = viewResolver(viewName);

        // Render: model을 인자로 함께 넘겨야 한다.
        view.render(mv.getModel(), request, response);
    }

    /**
     * HttpServletRequest의 모든 parameter를 가져와 {이름, 파라미터 값}을 paramMap에 저장 후 반환하는 메소드
     * (Logic의 level을 맞추기 위해 별도의 메소드로 추출)
     * @param request
     * @return paramMap
     */
    private Map<String, String> createParamMap(HttpServletRequest request) {

        Map<String, String> paramMap = new HashMap<>();

        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));

        return paramMap;
    }

    /**
     * 논리 이름을 물리적인 위치 이름으로 바꾸는 메소드
     * (Logic의 level을 맞추기 위해 별도의 메소드로 추출)
     * @param viewName
     * @return 물리 이름을 인자로 가지는 MyView
     */
    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");  // 물리 위치 이름(예: /WEB-INF/views/new-form.jsp)
    }


}
