package com.shop.repository;


import com.shop.entity.Order;
import com.shop.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository <엔티티 타입 클래스, 기본키 타입>
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{


}



