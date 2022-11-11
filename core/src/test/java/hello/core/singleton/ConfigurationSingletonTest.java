package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        // 서로 다른 두 구현 MemberServiceImpl, OrderServiceImpl은 둘 다 MemberRepository를 참조한다.
        // 그렇다면 서로 다른 두 개의 MemberRepository 구현 객체가 생성되는 것인데, 이는 singleton을 어긴 것이 아닌가?

        //해당 두 MemberRepository가 동일한지 알아보기 위한 테스트 코드
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        // 추가: 원본 memberRepository도 가져와 비교해보자.
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        System.out.println("memberService -> memberRepository = " + memberRepository1);
        System.out.println("orderService -> memberRepository = " + memberRepository2);
        // 추가: 원본 memberRepository
        System.out.println("memberRepository = " + memberRepository);

        // 두 MemberRepository는 같은 객체임을 확인할 수 있다.
        // 원본 memberRepository 역시 같은 객체이다.

        Assertions.assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        Assertions.assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);

        // AppConfig에서 MemberServiceImpl, OrderServiceImple이 서로 다른 new로 각각 memberRepository를 생성하였는데, 어떻게 두 객체가 같은 걸까?
        // AppConfig에 print를 넣어 확인해보면 MemberRepository가 3회가 아닌 1회만 호출되는 것을 볼 수 있다.
    }

    @Test
    void configurationDeep() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean = " + bean.getClass());
        /*
        * getClass()로 bean의 class type을 출력: hello.core.AppConfig가 나와야 한다.
        * 그러나 실행 시 hello.core.AppConfig$$EnhancerBySpringCGLIB$$5bbd9900가 출력된다.
        * 이는 @Configuration annotation에 의해 spring이 바이트코드 조작 라이브러리를 사용하여 AppConfig 클래스를 상속받은 임의의 다른 클래스를 만들고, 이것을 Spring bean으로 등록하였기 때문이다.
        * spring container에는 이름은 appConfig이지만 instance가 AppConfig@CGLIB인 bean이 등록되어 있다.
        * 해당 바이트코드 조작 라이브러리에 의해 spring에서는 singleton이 보장된다.
        * (라이브러리 내부에선 bean이 등록되어 있지 않을 때만 생성하고, 이미 bean이 spring container에 등록되어 있으면 기존 bean을 호출하는 동작이 실행될 것이다.)
        * @Configuration이 없어도 bean 등록은 정상적으로 기능하지만, 서로 다른 memberRepository 객체를 만들어 singleton이 깨지며, 그렇게 새로 만들어진 MemoryMemberRepository를 Spring container가 관리하지 않는다.
        * */
    }
}
