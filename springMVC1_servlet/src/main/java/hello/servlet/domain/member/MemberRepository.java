package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Member 데이터를 저장하는 임시 DB(memory)
 * 동시성 문제가 고려되어 있지 않음: 실무에서는 ConcurrentHashMap, AtomicLong 사용을 고려
 */
public class MemberRepository {

    // static: MemberRepository가 다수 생성되어도 Map<>과 sequence는 하나만 생성됨
    private static Map<Long, Member> store= new HashMap<>();
    private static long sequence = 0L;  // id 증가 sequence

    // Singleton
    private static final MemberRepository instance = new MemberRepository();

    public static MemberRepository getInstance() {
        return instance;
    }
    // singleton은 접근을 제한하여야 함: 생성자를 private로 제한, getInstance로만 접근 가능
    private MemberRepository() {
    }

    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());  // 새로운 ArrayList에 넣어 반환: HashMap store의 값을 보호
    }

    public void clearStore() {
        store.clear();
    }
}