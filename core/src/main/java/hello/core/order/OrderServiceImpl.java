package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

    // OrderServiceImpl이 Interface(MemberRepository, DiscountPolicy)뿐만 아니라 구현체(MemoryMemberRepository, FixDiscountPolicy)를 동시에 의존하고 있다.
    // 따라서 구현체를 변경할 시 이 코드를 직접 수정해야 한다. -> OCP(변경에는 닫혀 있고, 확장에는 열려 있어야 한다)와 DIP(추상화에 의존하되, 구체화엔 의존하지 말아야 한다) 위반
    private final MemberRepository memberRepository;
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    // DIP 준수: 추상화에만 의존하도록 설계를 변경하고 구현체를 생성/주입하는 책임을 가지는 별개의 설정 클래스(AppConfig.java)를 만든다.
    private final DiscountPolicy discountPolicy;

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);  // OrderService는 할인 로직에 관여하지 않음: 단일 책임의 원칙 준수

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // For test
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
