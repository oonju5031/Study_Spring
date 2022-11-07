package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

// @SpringBootTest: Spring Container와 Test를 함께 실행한다.
@SpringBootTest
// @Transactional:
// 코드상에서 DB를 수정한 후에 Transaction을 종료하고 Commit을 해야 실제 DB에 반영이 되는데,
// Test Case에 @Transactional annotation이 있는 경우 Test 실행이 끝나면 Commit 대신 항상 Rollback을 하므로 실제 데이터에 관여하지 않는다.
// (@Transactional annotation 자체는 메소드가 트랜젝션이 되도록 보장하는 기능으로, Test Case가 아니라 Service와 같은 실제 기능에 해당 annotation이 있으면 항상 rollback하지는 않는다.)
// Test 도중엔 실제로 DB에 Query를 송신하지만 이후 Rollback에 의해 원상태로 복구된다.
// 이는 beforeEach, afterEach를 대체하는 효과가 있다.
@Transactional
// 통합 테스트: Spring Container와 DB까지 연동하여 진행하는 Test이다.
class MemberServiceIntegrationTest {

    // Test시에는 필드 DI를 해도 크게 상관없음: 다른 데서 가져다 쓸 것도 아니고...
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;  // Interface를 선언: 구현은 SpringConfig을 통해 구현될 것



    @Test
    void join() {
        // given(주어진 상황)
        Member member = new Member();
        member.setName("hello");
        
        // when(실행하였을 때)
        Long saveId = memberService.join(member);

        // then(나와야 하는 결과)
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    }

}