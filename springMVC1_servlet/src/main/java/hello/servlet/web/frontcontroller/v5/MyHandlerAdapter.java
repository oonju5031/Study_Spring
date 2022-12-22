package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * MVC Pattern with V5:
 * adapter pattern은 front controller가 한 가지 방식이 아닌, 다양한 방식의 controller(예: ControllerV3, ControllerV4)를 처리할 수 있도록 만들어 준다.
 */
public interface MyHandlerAdapter {

    /*
    Object handler는 controller를 의미한다(handler가 보다 포괄적인 범위이다).
    supports()는 특정 controller를 입력하였을 때 adapter가 해당 controller를 처리할 수 있는지 판단하는 메소드이다.
     */
    boolean supports(Object handler);

    /*
    이전에는 front controller가 직접 실제 controller를 호출하였지만, 이제는 해당 adapter를 통하여 controller가 호출된다.
    Adapter는 실제 controller를 호출하고 그 결과로 ModelView를 반환하여야 하며, 실제 controller가 ModelView를 반환하지 못하면 adapter에서 직접 생성해서 반환할 수 있어야 한다.
     */
    ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException;
}
