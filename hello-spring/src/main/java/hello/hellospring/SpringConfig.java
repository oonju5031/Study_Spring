package hello.hellospring;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    // For JDBC: Spring에서 제공하는 DataSource
//    private DataSource dataSource;
//
//    @Autowired
//    public SpringConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

    // For JPA
//    private EntityManager em;
//
//    public SpringConfig(EntityManager em) {
//        this.em = em;
//    }

    // For Spring Data JPA
    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    public MemberService memberService() {
//        return new MemberService(memberRepository());
        return new MemberService(memberRepository);
    }


//    @Bean
    // Spring의 DI를 사용한 다형성 활용: 하단 Assembly(Application을 설정하는 코드)만 변경하면 다른 코드를 수정하지 않으면서 Repository를 변경 가능
//    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();  // Memory
//        return new JdbcMemberRepository(dataSource);  // 순수 JDBC
//        return new JdbcTemplateMemberRepository(dataSource);  // JDBC Template
//        return new JpaMemberRepository(em);  // JPA
//    }
}
