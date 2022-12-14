package hello.servlet.web.frontcontroller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
}
