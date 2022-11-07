package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    // JPA는 Entity Manager로 모든 동작을 제어한다(DB와의 통신 포함).
    // 이는 Spring Boot에서 자동으로 생성하므로, Injection만 진행하면 된다.
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);  // member가 null값일 때를 고려함
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("SELECT m FROM Member m WHERE m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        // JPQL(객체지향 쿼리 언어): Member라는 Entity(즉 객체)를 상대로 Query를 보내면 이것이 SQL로 번역된다.
        // SELECT m: 객체 자체를 SELECT할 수 있는 점에 주목.
        return em.createQuery("SELECT m FROM Member m", Member.class)
                .getResultList();
    }
}
