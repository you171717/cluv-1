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

    @ManyToOne (fetch = FetchType.LAZY)      // 지연로딩
    @JoinColumn(name ="item_id")
    private Item item;                       // 다대일 단방향 매핑

    @ManyToOne(fetch = FetchType.LAZY)       // 지연로딩
    @JoinColumn(name = "order_id")
    private Order order;                     // 다대일 단방향 매핑

    private int orderPrice;                  // 주문 가격

    private int count;                       // 수량

//    private LocalDateTime regTime;
//
//    private LocalDateTime updateTime;

}
