package hello.servlet.web.frontcontroller.v2.controller;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.ControllerV2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * MVC Pattern with FrontController V2:
 * 해당 controller로의 호출, 해당 컨트롤러가 수행하는 view 호출 둘 다 FrontController에서 담당
 * 호출 관련 기능을 FrontController에 위임하여 단순히 로직만을 가진 controller
 */
public class MemberFormControllerV2 implements ControllerV2 {
    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // FormController는 로직이 없으니 단순히 viewPath만 지정한 MyView를 반환한다.
        return new MyView("/WEB-INF/views/new-form.jsp");
    }
}
