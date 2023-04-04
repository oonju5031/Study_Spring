package hello.login.web.login;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;
	private final SessionManager sessionManager;

	@GetMapping("/login")
	public String loginForm(@ModelAttribute LoginForm form) {
		return "login/loginForm";
	}

	/**
	 * V1: cookie 사용
	 */
	// @PostMapping("/login")
	public String loginV1(@Valid @ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult, HttpServletResponse response) {
		if (bindingResult.hasErrors()) {
			return "login/loginForm";
		}

		Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
		log.info("login? {}", loginMember);

		if (loginMember == null) {
			bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
			return "login/loginForm";
		}

		// 로그인 성공

		// 쿠키 생성: 시간 정보를 주지 않으면 세션 쿠키(브라우저 종료 시 만료)
		Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
		response.addCookie(idCookie);

		return "redirect:/";

	}

	/**
	 * V2: 직접 만든 session 사용
	 */
	// @PostMapping("/login")
	public String loginV2(@Valid @ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult, HttpServletResponse response) {
		if (bindingResult.hasErrors()) {
			return "login/loginForm";
		}

		Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
		log.info("login? {}", loginMember);

		if (loginMember == null) {
			bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
			return "login/loginForm";
		}

		// 로그인 성공

		// 세션 관리자를 통해 세션 생성 및 회원 데이터 보관
		sessionManager.createSession(loginMember, response);

		return "redirect:/";

	}
	/**
	 * V3: 서블릿 HTTP 세션 사용
	 */
	@PostMapping("/login")
	public String loginV3(@Valid @ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			return "login/loginForm";
		}

		Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
		log.info("login? {}", loginMember);

		if (loginMember == null) {
			bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
			return "login/loginForm";
		}

		// 로그인 성공

		// 세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
		HttpSession session = request.getSession();
		// 세션에 로그인 회원 정보 보관
		session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

		return "redirect:/";
	}

	// @PostMapping("/logout")
	public String logoutV1(HttpServletResponse response) {
		expireCookie(response, "memberId");
		return "redirect:/";
	}

	// @PostMapping("/logout")
	public String logoutV2(HttpServletRequest request, HttpServletResponse response) {
		sessionManager.expireSession(request);
		return "redirect:/";
	}

	@PostMapping("/logout")
	public String logoutV3(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		return "redirect:/";
	}

	/**
	 * 쿠키 삭제
	 * @param response Http response
	 * @param cookieName 삭제할 쿠키 이름
	 */
	private static void expireCookie(HttpServletResponse response, String cookieName) {
		Cookie cookie = new Cookie(cookieName, null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}
}
