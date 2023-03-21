package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

    /*@GetMapping("/")*/
    public String home() {
        return "home";
    }

    @GetMapping("/")
    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {

        // 쿠키가 없는 경우
        if (memberId == null) {
            return "home";
        }

        Member loginMember = memberRepository.findById(memberId);

        // 해당 쿠키에 해당하는 값이 DB에 없는 경우(ex: DB 수정, 쿠키 만료)
        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "homeLogin";
    }
}