package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name="frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    private final Map<String, Object> handlerMappingMap = new HashMap<>();  // 여러 종류의 Controller를 입력받을 수 있어야 하기에 구체적인 컨트롤러(예: ControllerV4) 대신 Object타입을 사용한다.
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();  // 존재하는 MyHandlerAdapter를 명시하는 핸들러 어댑터 목록

    /*
    생성자로 Mapping 정보 입력: 함수를 별도로 분리
     */
    public FrontControllerServletV5() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    /**
     * 핸들러 매핑 정보
     */
    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }

    /**
     * 핸들러 어댑터 목록
     */
    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    /*
    service() 로직은 FrontControllerServletV3과 유사하나 controller 대신 handler를 사용한다.
    기존 로직을 기능별로 나누어 별도의 메소드로 분리하였다.
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 핸들러 찾기
        Object handler = getHandler(request);

        // 핸들러가 없는 경우 404 not found 반환
        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 찾은 핸들러에 맞는 핸들러 어댑터 찾기
        // (핸들러 어댑터가 없는 경우 IllegalArgumentException 발생)
        MyHandlerAdapter adapter = getHandlerAdapter(handler);

        // 핸들러 어댑터(ControllerV3HandlerAdapter) 호출 -> 핸들러(ControllerV3) 호출 -> ModelView 반환
        ModelView mv = adapter.handle(request, response, handler);

        // 논리 이름 -> 물리 위치 이름
        String viewName = mv.getViewName();
        MyView view = viewResolver(viewName);

        // Render
        view.render(mv.getModel(), request, response);
    }

    /**
     * 핸들러 매핑 정보(생성자에 명시됨)에서 알맞는 핸들러(=컨트롤러)를 조회한다.
     * @param request
     * @return handler
     */
    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }

    /**
     * 핸들러 어댑터 목록(handlerAdapters)에서 핸들러를 처리할 수 있는 어댑터를 조회한다.
     * @param handler
     * @return adapter
     */
    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("다음 handler에 대한 handler adapter를 찾을 수 없습니다: " + handler);
    }

    /**
     * 논리 이름을 물리적인 위치 이름으로 바꾸는 메소드
     * @param viewName(논리 이름)
     * @return 물리 위치 이름을 인자로 가지는 MyView
     */
    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");  // 물리 위치 이름(예: /WEB-INF/views/new-form.jsp)
    }
}
