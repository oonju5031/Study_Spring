package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

    /*@GetMapping("/")*/
    public String home() {
        return "home";
    }

    /**
     * V1: cookie 사용
     */
    // @GetMapping("/")
    public String homeLoginV1(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {

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

    /**
     * V2: 직접 만든 session 사용
     */
    // @GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model) {

        // 세션 관리자에 저장된 회원 정보 조회
        Member member = (Member)sessionManager.getSession(request);

        // 로그인
        if (member == null) {
            return "home";
        }

        model.addAttribute("member", member);
        return "homeLogin";
    }

    /**
     * V3: 서블릿 HTTP 세션 사용
     */
    /*@GetMapping("/")*/
    public String homeLoginV3(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);

        if (session == null) {
            return "home";
        }

        Member loginMember = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);

        // 세션에 회원 데이터가 없으면 home으로 이동
        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "homeLogin";
    }

    /**
     * V3Spring: SessionAttribute 추가
     */
    @GetMapping("/")
    public String homeLoginV3Spring(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {

        // 세션에 회원 데이터가 없으면 home으로 이동
        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "homeLogin";
    }

}