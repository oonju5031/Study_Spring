package hello.servlet.web.sevletmvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * MVC Pattern:
 * view(new-form.jsp)를 호출하는 controller
 */
@WebServlet(name = "mvcMemberFormServlet", urlPatterns = "/servlet-mvc/members/new-form")
public class MvcMemberFormServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // View 호출
        String viewPath = "/WEB-INF/views/new-form.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);  // RequestDispatcher: controller에서 view로 이동할 때 사용
        dispatcher.forward(request, response);  // 다른 servlet이나 JSP를 '서버 내부에서' 호출한다.(=페이지 전환)
    }
}
/*
 * Controller: Servlet
 * View: JSP
 * Model: HttpServletRequest(request는 내부에 데이터 저장소를 가진다.)

MVC pattern은 항상 controller를 거쳐서 view에 접근하여야 한다. 즉 모든 요청은 controller로 들어와야 한다.
해당 클래스(/servlet-mvc/members/new-form)가 호출될 시 RequestDispatcher는 forward(request, response)를 통해 '서버 내부에서' viewPath가 가리키는 페이지(/WEB-INF/views/new-form.jsp)를 호출하게 된다.

redirect: client에 응답이 반환되었다가 다시 server에 redirect 경로로 호출 요청이 옴(=URL이 변경됨)
forward: 응답이 반환되지 않고 서버 내부에서 호출함(=URL이 변경되지 않음)

* WEB-INF폴더 내에 있는 자원들은 외부에서 호출할 수 없으며(=URL로 접근할 수 없다), 항상 controller를 통해 접근하여야 한다.
 */