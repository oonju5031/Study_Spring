package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Controller는 Static한 html파일보다 우선권을 가진다. -> 해당 Controller가 있으면 index.html이 동작하지 않음
    @GetMapping("/")
    public String home() {
        return "home";
    }
}
