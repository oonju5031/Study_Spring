package hello.servlet;

import hello.servlet.web.springmvc.v1.SpringMemberFormControllerV1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

@ServletComponentScan  // 서블릿 자동 등록(현재 패키지 및 하위 패키지에서)
@SpringBootApplication
public class ServletApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServletApplication.class, args);
	}

	// Component scan을 이용하지 않고 직접 spring bean으로 등록하는 방법
	/*@Bean
	SpringMemberFormControllerV1 springMemberFormControllerV1() {
		return new SpringMemberFormControllerV1();
	}*/
}
