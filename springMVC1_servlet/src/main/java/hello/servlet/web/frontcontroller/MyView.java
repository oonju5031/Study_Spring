package hello.servlet.web.frontcontroller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * MVC Pattern with FrontController V2:
 * view로의 요청을 담당하는 Controller
 */
public class MyView {
    private String viewPath;

    // 생성자
    public MyView(String viewPath) {
        this.viewPath = viewPath;
    }

    // View를 호출하여 jsp를 rendering하는 method
    public void render(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }

    // MVC Pattern with FrontControoler V3: model을 함께 전달받는 render()
    public void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        modelToRequestAttribute(model, request);  // model에 있는 data를 request의 attribute로 바꾼다.

        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }

    private void modelToRequestAttribute(Map<String, Object> model, HttpServletRequest request) {
        model.forEach((key, value) -> request.setAttribute(key, value)); // model의 모든 값을 request.setAttribute로 저장한다.
    }
}
