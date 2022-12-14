package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest1 {

    // Prototype bean만 사용
    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);
    }

    // Singleton 내부에서 Prototype bean을 사용
    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);
    }

    @Scope("singleton")  // "singleton"은 굳이 명시할 필요 없다.
    static class ClientBean {

        // Provider를 이용한 DL(Dependency Lookup; 의존관계 조회(검색): 필요한 의존관계를 주입받는(DI) 게 아닌 직접 찾는 것)
        @Autowired
//        private ObjectProvider<PrototypeBean> prototypeBeanProvider;  // Spring의 ObjectProvider(Spring에 의존하나 편의기능이 다양함)
        private Provider<PrototypeBean> prototypeBeanProvider;  // javax의 JSR330 Provider(Java 표준이기에 Spring이 아닌 컨테이너에서도 사용 가능)

        public int logic() {
//            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();  // Spring의 ObjectProvider
            PrototypeBean prototypeBean = prototypeBeanProvider.get();  // javax의 JSR330 Provider
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init = " + this);
        }

        // Prototype bean: 호출되지 않음
        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
