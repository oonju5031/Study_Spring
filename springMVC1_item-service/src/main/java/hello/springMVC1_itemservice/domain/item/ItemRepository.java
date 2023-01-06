package hello.springMVC1_itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 상품 저장소: 예제이므로 메모리를 이용한다.
 */
@Repository // @Component를 상속해 component scan의 대상이 된다.
public class ItemRepository {

    /*
     1. 실무의 경우 동시성 문제 때문에(예: 여러 thread가 동시에 접근하는 경우) HashMap, long을 사용하면 안 된다. 대신 ConcurrentHashMap, AtomicLong 등을 사용하여야 한다.
     2. store와 sequence는 static으로, 전역에서 정의된다. Spring은 기본적으로 singleton이므로 굳이 명시할 필요는 없지만, 다른 곳에서 새로 만들 때를 고려하여 static으로 만든다.
     */
    private static final Map<Long, Item> store = new HashMap<>();
    private static long sequence = 0L;

    /**
     * 상품 객체를 저장소에 저장한다.
     * @param item 저장할 상품 객체
     * @return item: 저장된 상품 객체
     */
    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    /**
     * ID값을 입력받아 저장소 내 상품을 조회한다.
     * @param id 찾을 상품의 id값
     * @return Item: 해당 id값을 가지는 상품
     */
    public Item findById(Long id) {
        return store.get(id);
    }

    /**
     * 저장소 내 모든 상품 목록을 조회한다.
     * @return ArrayList: 저장소 내 상품 리스트
     */
    public List<Item> findAll() {
        return new ArrayList<>(store.values());
        /*
        store.values()를 반환하여 이후 type을 캐스팅해도 되지만,
        ArrayList가 수정되어도 실제 저장소 store에 영향이 가지 않게 하기 위해 ArrayList로 감싸서 반환한다.
         */
    }

    /**
     * ID값을 입력받아 저장소 내 상품을 수정한다.
     * @param itemId 수정할 상품의 id값
     * @param updateParam 수정할 내용: id값을 가지지 않음
     */
    // updateParam은 Item과는 별도의 객체(ItemParamDTO)로 만드는 것이 합리적이나, 간단한 예제이므로 Item에서 생성자만 추가하여 사용한다.
    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);

        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    /**
     * 저장소 내 객체들을 모두 삭제한다.(테스트용)
     */
    public void clearStore() {
        store.clear();
    }
}
