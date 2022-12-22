package hello.servlet.web.frontcontroller;

import java.util.HashMap;
import java.util.Map;

/**
 * MVC Pattern with FrontController V3:
 * 1. request 객체를 model로 사용하는 대신 별도의 model객체를 사용할 수 있도록 한다.
 *     -> controller의 servlet 종속성을 제거한다(controller가 servlet, 즉 HttpServletRequest와 HttpServletResponse를 몰라도 동작할 수 있도록 한다).
 * 2. View 이름도 함께 전달한다.
 *     -> 논리 이름(예: new-form)을 전달하며, 물리 이름(예: /WEB-INF/views/*.jsp)은 FrontController에 1회만 선언된다. 추후 물리적으로 폴더가 변경될 때 FrontController만 고치면 된다.
 * (Spring MVC의 ModelAndView와 유사함)
 */
public class ModelView {
    private String viewName;  // view의 논리 이름 (= View 정보)
    private Map<String, Object> model = new HashMap<>();  // 요청 파라미터 정보를 저장하는 Map (= Model 정보)

    public ModelView(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }
}
