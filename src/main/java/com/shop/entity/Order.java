package com.shop.entity;

import com.shop.constant.GiftStatus;
import com.shop.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.annotations.Many;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;              // 다대일 단방향 매핑

    private LocalDateTime orderDate;    // 주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;    // 주문 상태

    @Enumerated(EnumType.STRING)
    private GiftStatus giftStatus;      // 구매/선물 상태

    private String sendYn;              // 메세지 전송 유무

    // 영속성 상태 변화를 자식 엔티티에 모두 전이
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,  // 일대다 매핑, mappedBy 속성 : 연관 관계 주인 설정
                orphanRemoval = true, fetch = FetchType.LAZY)
    // 고아 객체 제거 사용
    private List<OrderItem> orderItems =new ArrayList<>();

//    private LocalDateTime regTime;
//    private LocalDateTime updateTime;

    // 주문 상품 정보 - orderItem 객체를 order 객체의 orderItems에 추가
    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);        // 양방향 참조이므로 orderItem 객체에도 order 객체 세팅
    }


    public static Order createOrder(Member member, List<OrderItem> orderItemList){
        Order order = new Order();
        order.setMember(member);          // 상품 주문한 회원 정보 세팅
        for(OrderItem orderItem : orderItemList){       // 여러개의 주문 상품을 위한 리스트
            order.addOrderItem(orderItem);
        }
        order.setOrderStatus(OrderStatus.ORDER);        // 주문 상태를 order로 세팅
        order.setOrderDate(LocalDateTime.now());        // 현재 시간을 주문 시간으로 세팅
        return order;
    }

    // 총 주문 금액
    public int getTotalPrice(){
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    // 주문 취소
    public void cancelOrder(){
        this.orderStatus = OrderStatus.CANCEL;

        for(OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }
}
