package hello.core.discount;

import hello.core.AppConfig;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class RateDiscountPolicyTest {

    RateDiscountPolicy rateDiscountPolicy = new RateDiscountPolicy();
    MemberService memberService;

    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }

    @Test  // 성공 테스트
    @DisplayName("VIP에 한해 10% 할인이 적용되어야 한다.")
    void vipY() {
        // given
        Member member = new Member(1L, "memberVIP", Grade.VIP);
        memberService.join(member);

        // when
        int discount = rateDiscountPolicy.discount(member, 10000);

        // then
        assertThat(discount).isEqualTo(1000);
    }

    @Test  // 실패 테스트
    @DisplayName("VIP가 아니라면 할인이 적용되지 않아야 한다.")
    void vipN() {
        // given
        Member member = new Member(2L, "memberBASIC", Grade.BASIC);
        memberService.join(member);

        // when
        int discount = rateDiscountPolicy.discount(member, 10000);

        // then
        assertThat(discount).isEqualTo(0);
    }

}