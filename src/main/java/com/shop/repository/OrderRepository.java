package com.shop.repository;


import com.shop.entity.Order;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// JpaRepository <엔티티 타입 클래스, 기본키 타입>
public interface OrderRepository extends JpaRepository<Order, Long>{


    @Query("select o from Order o " +
            "where o.member.email = :email " +
            "order by o.orderDate desc"
    )
    List<Order> findOrders(@Param("email") String email, Pageable pageable);

    @Query("select count(o) from Order o " +
           "where o.member.email = :email"
     )
    Long countOrder(@Param("email") String email);


}



