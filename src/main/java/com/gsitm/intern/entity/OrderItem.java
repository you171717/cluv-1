package com.gsitm.intern.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem extends BaseEntity{  //상속받음으로 기존 regTime, updateTime 변수 삭제
    
    @Id @GeneratedValue
    @Column(name="order_item_id")
    private Long id;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;  //하나의 상품은 여러 주문 상품으로 들어갈 수 있음
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order;
    
    private int orderPrice; //주문가격
    
    private int count;  //수량

    public static OrderItem createOrderItem(Item item, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setCount(count);
        orderItem.setOrderPrice(item.getPrice());  //현재 시간 기준으로 주문 가격을 상품 가격으로 세팅

        item.removeStock(count);
        return orderItem;
    }

    public int getTotalPrice() {
        return orderPrice*count;
    }

    public void cancel() {
        this.getItem().addStock(count);
    }
}
