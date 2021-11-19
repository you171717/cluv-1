package com.shop.entity;

import com.shop.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate; //주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; //주문상태

    //주문 상품 엔티티와 일대다 매핑
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.LAZY)
    // 하나의 주문이 여러 개의 주문 상품 갖는다 -> List 자료형 사용해 매핑
    private List<OrderItem> orderItems = new ArrayList<>();

    //private LocalDateTime regTime;

    //private LocalDateTime updateTime;

    // 7장 주문 상품 < 생성한 주문 상품 ㅁ객체 이용해 주문 객체 만들기>

    public void addOrderItem(OrderItem orderItem){ // orderitems => 주문 상품 정보
        //orderItem 객체를 order 객체의 orderItems에 추가
        orderItems.add(orderItem);
        //Order Entity와 ORderItem Entity는 양방향 참초 관계
        //orderItem 객체에도 order 객체 세팅
        orderItem.setOrder(this);
    }

    public static Order createOrder(Member member, List<OrderItem> orderItemList){
        Order order = new Order();
        order.setMember(member);    // 상품을 주문한 회원의 정보를 세팅
        //상품 페이지에서는 1개의 상품 주문 , 장바구니 페이지에서는 한번에 여러개 상품 주문 가능

        for(OrderItem orderItem : orderItemList){ //<-여러개의 주문 상품 담을 수 있도록
            // 리스트 형태로 파라미터 값 받고 주문 객체에 orderItem 객체 추가
            order.addOrderItem(orderItem);
        }
        order.setOrderStatus(OrderStatus.ORDER); // 주문 상태 order로 표시
        order.setOrderDate(LocalDateTime.now()); // 현재 시간 주문시간으로 세팅
        return order;

    }
    public int getTotalPrice(){     // 총 주문 금액 구하는 메소드
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }


}

