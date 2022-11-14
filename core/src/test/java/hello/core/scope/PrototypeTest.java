package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class PrototypeTest {

    @Test
    void prototypeBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);  // PrototypeBean class가 자동으로 bean으로 등록되어 @Component가 필요하지 않음.
        System.out.println("find prototypeBean1");
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        System.out.println("find prototypeBean2");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2);
        Assertions.assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

//        prototypeBean1.destroy();  // @PreDestroy가 필요한 경우 이와 같이 직접 실행해주어야 한다. 관리할 책임이 클라이언트에게 있기 때문이다.
        ac.close();
        // 해당 코드 실행 시 1. 요청받은 시점에 bean을 생성하며, 2. 두 bean이 다른 객체이며, 3. close() 직전에 @PreDestroy가 실행되지 않는 것을 볼 수 있다.
    }

    /*
    Bean Scope "prototype":
    Sngleton bean과 달리 Spring container가 Prototype bean의 생성과 의존관계 주입(초기화)까지만 관여하고 더 이상은 관리하지 않는다. 즉 매우 짧은 범위를 가진다.
    Singleton bean이 항상 같은 인스턴스의 스프링 빈을 반환하는 것과 달리, Prototype bean은 항상 새로운 인스턴스를 생성하여 반환한다.
    생성되는 시점은 요청이 들어올 때이다.
    Spring container는 Prototype bean을 관리할 책임을 가지지 않으므로, 해당 책임은 bean을 받은 클라이언트에게 전가된다.
    이는 @PreDestroy와 같은 종료 메소드가 동작하지 않는다는 것을 뜻한다.
     */
    @Scope("prototype")
    static class PrototypeBean {
        @PostConstruct
        public void init() {
            System.out.println("SingletonBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("SingletonBean.destroy");
        }
    }
}
