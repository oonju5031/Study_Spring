package hello.core.member;

import hello.core.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {

    MemberService memberService;

    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }

    @Test
    void join() {
        // given: 어떠한 환경이 주어졌을 때
        Member member = new Member(1L, "memberA", Grade.VIP);

        // when: 어떠한 작업을 하면 작업을 하면
        memberService.join(member);
        Member findMember = memberService.findMember(1L);

        // then: 다음과 같은 결과가 나온다.
        Assertions.assertThat(member).isEqualTo(findMember);
    }
}
