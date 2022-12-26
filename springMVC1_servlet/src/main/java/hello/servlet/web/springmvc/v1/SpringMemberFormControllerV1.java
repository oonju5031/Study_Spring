package hello.servlet.web.springmvc.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Spring MVC Controller V1:
 * Annotation을 기반으로 한다.
 * @Component를 상속한 @Controller에 의해 component scan의 대상이 되며, spring이 해당 클래스를 자동으로 spring bean으로 등록한다.
 */
@Controller
public class SpringMemberFormControllerV1 {

    @RequestMapping("/springmvc/v1/members/new-form")
    public ModelAndView process() {
        // 논리 이름 반환: 물리 위치 이름 변환을 담당하는 viewResolver는 application.properties에 정의되어 있다.
        return new ModelAndView("new-form");
    }
}
