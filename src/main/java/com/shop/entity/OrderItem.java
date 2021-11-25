package com.shop.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem extends BaseEntity {

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

    private int amount_price; // 포인트 사용 후 주문 가격

    private int count; //수량

    private int input_point; // 사용된 포인트

    public static OrderItem createOrderItem(Item item, int count, int input_point, int amount_price){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setCount(count);
        orderItem.setInput_point(input_point);
        orderItem.setAmount_price(amount_price);
        orderItem.setOrderPrice(item.getPrice());
        item.removeStock(count);
        return orderItem;
    }

    public int getTotalPrice(){
        return orderPrice*count;
    }

    public void cancel() {
        this.getItem().addStock(count);
    }

}