package hello.servlet.basic;

import lombok.Getter;
import lombok.Setter;

/**
 * JSON으로 파싱할 수 있도록 생성한 객체:
 * 일반적으로 JSON은 그대로 사용하지 않으며, 객체(Class 등)으로 바꾸어 사용한다.
 */
@Getter @Setter // Lombok: Getter와 Setter를 Annotation으로 제공
public class HelloData {

    private String username;
    private int age;

}
