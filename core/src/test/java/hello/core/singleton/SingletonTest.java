package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.*;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();

        // 조회 1: 호출할 때마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();
        // 조회 2: 호출할 때마다 객체를 생성
        MemberService memberService2 = appConfig.memberService();

        // 조회 1과 2에서 참조값이 다른 것(별개의 객체인 것)을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        // memberService1 != memberService2
        assertThat(memberService1).isNotSameAs(memberService2);

        // 스프링을 사용하지 않는 순수한 DI 컨테이너인 AppConfig는 요청을 할 때마다 객체를 새로 생성한다.
        // 해당 함수에서는 memberService 객체 2개, memberService가 참조하는 memberRepository 객체 2개 총 4개의 객체를 생성하였다.
        // 웹 어플리케이션의 경우 고객의 요청이 굉장히 많기 때문에, 위와 같이 요청마다 객체를 생성하는 방식은 메모리에 부하가 굉장히 심하다.
    }


    // 위와 같은 문제를 해결하기 위해 하나의 객체만을 생성하여 객체 인스턴스를 공유하도록 설계하여야 한다.
    // 이러한 설계 방식을 싱글톤(Singleton)이라 하며, 이는 효율적인 재사용으로 메모리 용량의 관리를 돕는다.
    // Test를 실행하면 싱글톤을 적용한 해당 메소드가 싱글톤을 적용하지 않는 pureContainer() 메소드보다 압도적으로 빠른 것을 확인할 수 있다.
    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonSerivceTest() {
        // 새 객체를 생성하지 못하도록 private 생성자로 막았다.
//        new SingletonService();

        // 이미 생성되어 있는 하나의 SingletonService에 get으로 접근
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        // 조회 1과 2에서 참조값이 동일한 것(동일한 객체인 것)을 확인
        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);

        // singletonService1 == singletonService2
        assertThat(singletonService1).isSameAs(singletonService2);

        // isSameAs: 객체가 동일한가(=주소가 동일한가)
        // isEqualTo: 값이 동일한가(=내용이 동일한가)
    }

    // Spring Container는 기본적으로 모든 Bean 객체를 자동으로 Singleton으로 만들어 관리해준다.
    // 이는 Singleton이 가지는 단점들(난잡한 코드, 접근성 감소, OCP 및 DIP위반, 유연성 감소 등)을 해결해준다.
    // 별도의 설정이 있는 경우 새로운 Spring에서도 싱글톤이 아닌 요청마다 새로운 객체를 반환하는 기능도 사용할 수 있다.
    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        // 조회 1: 호출할 때마다 객체를 생성
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        // 조회 2: 호출할 때마다 객체를 생성
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        // 조회 1과 2에서 참조값이 동일한 것(동일한 객체인 것)을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        // memberService1 == memberService2
        assertThat(memberService1).isSameAs(memberService2);
    }

}
