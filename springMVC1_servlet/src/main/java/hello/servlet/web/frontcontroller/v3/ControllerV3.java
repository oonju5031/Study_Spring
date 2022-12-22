package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;

import java.util.Map;

/**
 * MVC Pattern with V3:
 * V2와 달리 Servlet(HttpServletRequest, HttpServletResponse)에 종속되지 않는다.
 */
public interface ControllerV3 {

    // ModelView를 반환하는 method: servlet을 필요로 하지 않는다.
    ModelView process(Map<String, String> paramMap);
}
