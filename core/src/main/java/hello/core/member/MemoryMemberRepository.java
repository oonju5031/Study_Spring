package hello.core.member;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

// DB가 아닌 Memory를 이용한 데이터 저장: DB가 구축될 때까지 임시로 사용. 서버를 내리면 데이터가 사라진다.
@Component
public class MemoryMemberRepository implements MemberRepository {

    // 동시성 이슈 때문에 HashMap보다는 ConcurrentHashMap이 적절하다.
    // ConcurrentHashMap: 내부적으로 동기화하므로 동시 멀티 쓰레드 어플리케이션에 적합함
    // HashMap: 내부적으로 동기화하지 않아 단일 쓰레드 프로그램에 적합함. 속도가 비교적 빠름.
    private static Map<Long, Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);  // key: memberId, Value: Member객체를 Hashmap store에 저장한다.
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);  // memberId라는 key를 이용해 Hashmap store에서 찾는다.
    }
}
