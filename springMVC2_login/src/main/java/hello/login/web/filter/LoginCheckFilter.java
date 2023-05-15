package hello.login.web.filter;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

	private static final String[] whiteList = {"/", "/members/add", "/login", "/logout", "/css/*"};

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String requestURI = httpRequest.getRequestURI();

		HttpServletResponse httpResponse = (HttpServletResponse) response;

		try {
			log.info("인증 확인 필터 시작: {}", requestURI);

			if (isLoginCheckPath(requestURI)) {
				log.info("인증 확인 로직 실행: {}", requestURI);
				HttpSession session = httpRequest.getSession(false);
				if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {  // 미인증 시
					log.info("인증되지 않은 사용자 요청: {}", requestURI);
					// 로그인 페이지로 redirect, parameter에 redirectURL을 추가하여 로그인 성공 시 기존 페이지로 돌아오도록 설정
					httpResponse.sendRedirect("/login?redirectURL=" + requestURI);
					return;
				}
			}

			chain.doFilter(request, response);
		} catch (Exception e) {
			throw e;  // 예외도 로깅 가능. but 톰캣까지 예외를 보내 주어야 함
		} finally {
			log.info("인증 확인 필터 종료: {}", requestURI);
		}
	}

	/**
	 * 화이트리스트 내 URI의 경우 인증을 검사하지 않는다.
	 * @param requestURI 사용자가 입력한 URI
	 * @return 해당 URL가 whiteList 내에 있으면 false, 없으면 true 반환
	 */
	private boolean isLoginCheckPath(String requestURI) {
		return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
	}
}
