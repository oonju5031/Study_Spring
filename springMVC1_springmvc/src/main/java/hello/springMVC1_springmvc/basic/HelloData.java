package hello.springMVC1_springmvc.basic;

import lombok.Data;

/**
 * HTTP 요청 파라미터: @ModelAttribute를 통해 값을 입력받을 객체
 */
/*
@Data는 다음과 같은 annotation 기능을 제공한다.

@Getter, @Setter, @ToString: Trivial
@EqualsAndHashCode: 두 객체의 내용이 같은지 동등성(equality)을 비교하는 메소드 equals() 제공
@RequiredArgsConstructor: 두 객체가 동일한 객체인지 동일성(identity)를 비교하는 메소드 hashCode() 제공
 */
@Data
public class HelloData {
    private String username;
    private int age;
}
