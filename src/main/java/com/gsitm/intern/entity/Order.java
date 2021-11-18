package com.gsitm.intern.entity;

import com.gsitm.intern.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter @Setter
public class Order extends BaseEntity {
    @Id @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;  //한 명의 회원은 여러번 주문가능

    private LocalDateTime orderDate;    //주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;    //주문상태

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    //부모 엔티티의 영속성 상태 변화를 자식 엔티티에 모두 전이하는 CascadeTypeAll 옵션 설정, 고아객체 제거
   private List<OrderItem> orderItems = new ArrayList<>();
    //하나의 주문이 여러 개의 주문 상품을 가지니까 List 자료형 사용해서 매핑

}
