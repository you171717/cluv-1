package com.gsitm.intern.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class OrderItem {

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

//    private LocalDateTime regTime; 삭제
    
//    private LocalDateTime updateTime; 삭제
}
