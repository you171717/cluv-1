package com.shop.entity;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //주문가격

    private int count; //수량

    //private LocalDateTime regTime;

    //private LocalDateTime updateTime;

    // 7장 주문 기능 구현하기
    public static OrderItem createOrderItem(Item item, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item); // 주문할 상품과 주문 수량 세팅
        orderItem.setCount(count); // 현재 시간 기준 - 상품 가격 주문으로 세팅
                                    // 상품 가격은 시간 따라 변동 가능성 있음
        orderItem.setOrderPrice(item.getPrice());

        item.removeStock(count); // 주문 수량만큼 상품의 재고 감소
        return orderItem;
    }

    public int getTotalPrice(){ //주문 가격과 주문 수량 곱해 해당 상품 주문한 총 가격 계산
        return orderPrice*count;
    }

    public void cancel() {
        this.getItem().addStock(count);
    }

}