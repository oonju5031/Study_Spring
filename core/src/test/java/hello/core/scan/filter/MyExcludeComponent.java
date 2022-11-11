package hello.core.scan.filter;

import java.lang.annotation.*;

@Target(ElementType.TYPE)  // Target: TYPE의 경우 Class 레벨에 사용하는 annotation임을 의미함
@Retention(RetentionPolicy.RUNTIME)  // Retention: annotation의 life cycle이 RUNTIME 동안 유지됨
@Documented

public @interface MyExcludeComponent {
}
