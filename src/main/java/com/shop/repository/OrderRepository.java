package com.shop.repository;


import com.shop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// JpaRepository <엔티티 타입 클래스, 기본키 타입>
public interface OrderRepository extends JpaRepository<Order, Long>{


}



