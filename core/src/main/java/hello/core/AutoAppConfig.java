package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
// @Component와 그 하위 annotation이 붙은 class들을 찾아 자동으로 spring bean으로 등록한다.
@ComponentScan(
        // 자동 등록에서 제외할 class를 지정할 수 있다: @Component가 있는 AppConfig.java를 등록하지 않기 위해 추가
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

}
