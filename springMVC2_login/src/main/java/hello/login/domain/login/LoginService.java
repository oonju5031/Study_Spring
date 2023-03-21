package hello.login.domain.login;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

	private final MemberRepository memberRepository;

	/**
	 * @param loginId  ID
	 * @param password 비밀번호
	 * @return Member(로그인 성공), null(로그인 실패)
	 */
	public Member login(String loginId, String password) {
		/*
		다음 코드를 람다식으로 변환
		Optional<Member> memberOptional = memberRepository.findByLoginId(loginId);
		Member member = memberOptional.get();
		if (password.equals(member.getPassword())) {
			return member;
		} else {
			return null;
		}
		*/

		return memberRepository.findByLoginId(loginId)
				.filter(m -> m.getPassword().equals(password))
				.orElse(null);
	}
}
