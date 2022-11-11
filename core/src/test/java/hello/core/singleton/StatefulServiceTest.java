package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // GET
        // Thread 1: 사용자 A가 10,000원 주문
        statefulService1.order("userA", 10000);
        // Thread 2: 사용자 B가 20,000원 주문
        statefulService2.order("userB", 20000);

        // WHEN
        // Thread 1: 사용자 A가 주문 금액을 조회
        int price = statefulService1.getPrice();

        // THEN
        // 사용자 A의 사용 금액인 10,000원이 나와야 하나, stateful한 변수 price의 값이 변경되어 올바른 결과가 나오지 않음.
        System.out.println("price = " + price);
        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
    }

    static class TestConfig {

        @Bean
        public StatefulService stateFulService() {
            return new StatefulService();
        }
    }

}