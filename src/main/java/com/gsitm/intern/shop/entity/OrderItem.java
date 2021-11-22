package com.gsitm.intern.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class OrderItem extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    //  fetch = FetchType.LAZY 방식으로 설정 (지연로딩방식) 2주차 과제에 포함
    //  실무에서는 매핑 되는 엔티티 개수가 많기 때문에 쿼리의 실행방식을 예측할 수 없다
    //  그래서 지연로딩방식을 사용한다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    //  fetch = FetchType.LAZY 방식으로 설정 (지연로딩방식) 2주차 과제에 포함
    //  실무에서는 매핑 되는 엔티티 개수가 많기 때문에 쿼리의 실행방식을 예측할 수 없다
    //  그래서 지연로딩방식을 사용한다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //주문가격

    private int count; //수량

    private LocalDateTime regTime;
    
    private LocalDateTime updateTime;

    public static OrderItem createOrderItem(Item item, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setCount(count);
        orderItem.setOrderPrice(item.getPrice());
        item.removeStock(count);
        return orderItem;
    }

    public int getTotalPrice(){
        return orderPrice*count;
    }

    public void cancel() {
        this.getItem().addStock(count);
    } // 주문 취소 시 주문 수량만큼 상품의 재고를 더해줍니다.

}