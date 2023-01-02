package hello.springMVC1_springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * HTTP 응답: 정적 리소스, 뷰 템플릿
 */
/*
Spring server에서 응답 데이터를 만드는 방법은 다음 세 가지가 있다.
1. 정적 리소스
    웹 브라우저에 정적인 HTML, css, js를 제공할 때 사용한다.
2. 뷰 템플릿 사용
    동적인 HTML을 제공할 때 사용한다.
3. HTTP 메시지 사용
    HTTP API를 제공하는 경우엔 HTML이 아니라 데이터를 전달하여야 하므로, HTTP message body에 JSON, xml과 같은 데이터를 넣어 전송한다.
 */
@Controller
public class ResponseViewController {

    /**
     * ModelAndView를 사용한 view 호출
     * Spring boot의 기본 view template 경로는 src/main/resources/templates이다.
     * 즉 다음 RequestMapping가 지정하는 View의 절대 경로는 src/main/resources/templates/response/hello.html이다.
     */
    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {

        ModelAndView modelAndView = new ModelAndView("/response/hello");
        modelAndView.addObject("data", "HelloV1");

        return modelAndView;
    }

    /**
     * V2: @Controller에서의 String 반환:
     * Annotation @ResponseBody가 없는 경우 view resolver에 의해 해당 String이 view의 논리적 이름으로 지정된다.
     * Annotation @ResponseBody가 있는 경우 message converter에 의해 해당 String이 그대로 HTTP message body에 입력된다.
     */
    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {

        model.addAttribute("data", "HelloV2");

        return "response/hello";
    }

    /**
     * V2: @Controller에서의 void 반환
     * Controller의 경로와 view의 논리적 이름이 같으며(/response/hello), 아무 반환을 하지 않으면 해당 값(=요청 URL)이 논리 view 이름이 된다.
     *
     * 명시성이 떨어지고 이런 식으로 맞아떨어지는 경우도 적기 때문에 권장하지 않는 방법이다.
     */
    @RequestMapping("/response/hello")
    public void responseViewV3(Model model) {

        model.addAttribute("data", "HelloV3");
    }
}
