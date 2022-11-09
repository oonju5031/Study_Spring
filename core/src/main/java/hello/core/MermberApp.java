package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MermberApp {
    public static void main(String[] args) {

        // DI를 사용하지 않음: OCP, DIP 위배
//        MemberService memberService = new MemberServiceImpl();

        // Constructor DI
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();

        // Spring container: AppConfig에 있는 @Bean이 붙은 method를 모두 호출하여 반환된 객체를 container에 등록함.
        // 이렇게 등록된 객체를 Spring bean이라 하며, 기본적으로 @Bean이 붙은 method명을 Spring bean의 이름으로 사용한다(@Bean(name="abc")로 변경 가능).
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        // getBean("꺼낼 메소드(객체)명", 반환 타입): key는 메소드명(memberService), value는 객체 인스턴스(MemberServiceImpl(memberRepository())
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

        Member member1 = new Member(1L, "memberA", Grade.VIP);  // Type이 Long이면 수 뒤에 L을 붙여줘야 한다.
        memberService.join(member1);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member = " + member1.getName());
        System.out.println("find member = " + findMember.getName());
    }
}
