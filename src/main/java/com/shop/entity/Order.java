package com.shop.entity;

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
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;              // 다대일 단방향 매핑

    private LocalDateTime orderDate;    // 주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;    // 주문 상태

    // 영속성 상태 변화를 자식 엔티티에 모두 전이
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,  // 일대다 매핑, mappedBy 속성 : 연관 관계 주인 설정
                orphanRemoval = true, fetch = FetchType.LAZY)                          // 고아 객체 제거 사용
    private List<OrderItem> orderItems =new ArrayList<>();

    private LocalDateTime regTime;

    private LocalDateTime updateTime;
}
