package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {
    /*
    주입할 spring bean이 없는 경우에도 동작해야 할 때, @Autowired만 사용하면 required = true 옵션에 의해 오류가 발생한다.
    이를 옵션을 이용해 처리하는 방법은 세 가지가 있다.
    1. @Autowired(required = false): 자동 주입할 대상이 없으면 setter method 자체가 호출되지 않는다.
    2. org.springframework.lang.@Nullable: 자동 주입할 대상이 없으면 null이 입력된다.
    3. Optional<>: 자동 주입할 대상이 없으면 Optional.empty가 입력된다.
     */

    @Test
    void AutowiredOption() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }

    static class TestBean {
        // Member는 Spring bean이 아니다. 이를 이용해 주입할 bean이 없을 때 처리하는 세 가지 방법을 다룬다.

        @Autowired(required = false)
        public void setNoBean1(Member noBean1) {
            System.out.println("noBean1 = " + noBean1);
        }

        @Autowired
        public void setNoBean2(@Nullable Member noBean2) {
            System.out.println("noBean2 = " + noBean2);
        }

        @Autowired
        public void setNoBean3(Optional<Member> noBean3) {
            System.out.println("noBean3 = " + noBean3);
        }
    }
}
