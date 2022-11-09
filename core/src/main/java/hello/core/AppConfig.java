package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 애플리케이션의 전체 동작 방식을 구성(config)하는 구성 영역 -> 구현 객체를 생성 및 연결하는 역할, 따라서 구현할 객체들을 모두 알고 있어야 함
// OCP와 DIP를 준수하기 위한 클래스: 클라이언트 객체(사용 영역)는 자신의 역할을 실행하는 것에만 집중할 수 있게 됨 -> 권한이 줄어들고 책임이 명확해짐(단일 책임 원칙 Single Responsibility Principle; SRT)
// 생성자(Constructor)를 이용한 의존성 주입(DI: Dependency Injection)을 사용
// 이처럼 제어의 역전(IoC: Inversion of Control), 즉 외부에서 객체의 생성 및 관리와 의존관계의 연결을 담당하는 코드를 IoC Container 또는 DI Container라고 한다(또는 Assembly나 Object factory라고도 한다).

// @Configuration annotation: Spring에서 설정 정보(구성 정보)를 담당하는 클래스
@Configuration
public class AppConfig {

    // @Bean annotation: 요소를 Spring Container에 등록
    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
