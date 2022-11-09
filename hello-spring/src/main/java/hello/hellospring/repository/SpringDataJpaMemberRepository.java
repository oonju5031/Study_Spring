package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Spring Data JPA: 구현체를 자동으로 생성해 준다.
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    // JPQL: SELECT m FROM Member m WHERE m.name = ?
    @Override
    Optional<Member> findByName(String name);
}
