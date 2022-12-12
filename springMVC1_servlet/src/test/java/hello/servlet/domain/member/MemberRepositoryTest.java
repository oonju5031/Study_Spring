package hello.servlet.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryTest {

    MemberRepository memberRepository = MemberRepository.getInstance();  // singleton이므로 new MemberRepository 안됨

    // Test가 끝날 때마다 초기화
    @AfterEach
    void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void save() {
        // given
        Member member = new Member("hello", 20);

        // when
        Member savedMember = memberRepository.save(member);

        // then
        Member findMember = memberRepository.findById(savedMember.getId());
        Assertions.assertThat(findMember).isEqualTo(savedMember);  // findById에서 반환된 findMember와 save에서 반환된 savedMember가 같은가?
    }

    @Test
    void findAll() {
        // given
        Member member1 = new Member("member1", 20);
        Member member2 = new Member("member2", 24);

        memberRepository.save(member1);
        memberRepository.save(member2);


        // when
        List<Member> result = memberRepository.findAll();

        // then
        Assertions.assertThat(result.size()).isEqualTo(2);  // result의 길이는 2인가?
        Assertions.assertThat(result).contains(member1, member2);  // result 내에 member1과 member2가 있는가?
    }
}