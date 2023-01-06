package hello.springMVC1_itemservice.domain.item;

import lombok.Data;

/**
 * 상품 객체
 */
/*
@Data는 핵심 도메인 모델에 사용하기엔 위험하다(예측하지 못한 동작이 생길 수 있다).
단순 데이터 전달에 사용되는 DTO와 같은 경우에는 사용하여도 무방하다.
 */
@Data
public class Item {

    private long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    /**
     * 기본 생성자
     */
    public Item() {

    }

    /**
     * ID값을 가지지 않는 생성자
     */
    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
