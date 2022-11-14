package hello.core.order;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

// Framework를 사용하지 않는 순수한 Java 코드 테스트
public class OrderServiceImplTest {

    @Test
    void createOrder() {
        /*
        setter DI의 경우 다음과 같은 문제점이 있다.
        1. OrderServiceImpl만 테스트하고 싶어도 OrderServiceImpl이 가진 의존관계를 전부 주입하여야 한다 -> Java코드로만 테스트할 수 없다.
        2. 해당 테스트 코드상에서 OrderServiceImpl의 의존관계가 어떠한지를 명시적으로 알 수 없어, OrderServiceImpl 코드를 따로 봐야 한다.
        */
//        OrderServiceImpl orderService = new OrderServiceImpl();
//        orderService.createOrder(1L, "itemA", 10000);

        /*
        이는 constructor DI로 해결할 수 있다.
        1. 의존관계를 가지는 memberRepository, discountPolicy에 임시 객체 또는 null값을 넣어 OrderServiceImpl만 테스트하는 코드를 만들 수 있다. -> 프레임워크 없이 Java코드로만 테스트 가능
        2. 매개변수를 통해 OrderServiceImpl이 어떤 의존관계를 가지는지 직관적으로 확인할 수 있다.
         */
        MemoryMemberRepository memberRepository = new MemoryMemberRepository();  // 임시 객체
        memberRepository.save(new Member(1L, "name", Grade.VIP));

        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, new FixDiscountPolicy());
        Order order = orderService.createOrder(1L, "itemA", 10000);

        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}
