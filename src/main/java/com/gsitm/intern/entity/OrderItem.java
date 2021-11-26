package com.gsitm.intern.entity;


import com.gsitm.intern.constant.ReturnStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 가격

    private int count; //수량

    //반품 갯수
    @Column(name = "return_count")
    private int returnCount;

    //반품 금액
    @Column(name = "return_price")
    private int returnPrice;

    //반품 요청일
    @Column(name = "return_req_date", nullable = true)
    private LocalDateTime returnReqDate;

    //반품 확정일
    @Column(name = "return_confirm_date", nullable = true)
    private LocalDateTime returnConfirmDate;

    //반품 여부
    @Enumerated(EnumType.STRING)
    @Column(name = "return_status")
    private ReturnStatus returnStatus;

    public static OrderItem createOrderItem(Item item, int count) {
        OrderItem orderitem = new OrderItem();
        orderitem.setItem(item);
        orderitem.setCount(count);
        orderitem.setOrderPrice(item.getPrice());

        item.removeStock(count);
        return orderitem;
    }

    public int getTotalPrice() {
        return orderPrice * count;
    }

    public void cancel() {
        this.getItem().addStock(count);
    }
}
