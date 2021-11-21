package com.gsitm.intern.shop.repository;

import com.gsitm.intern.shop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    //"select o from Order o" 가 아닌 "select o from Order o " o에서 띄워쓰기를 해줘야한다.
    @Query("select o from Order o " +
            "where o.member.email = :email " +
            "order by o.orderDate desc"
    )
    List<Order> findOrders(@Param("email") String email, Pageable pageable);

    //"select count(o) from Order o " 가 아닌 "select count(o) from Order o " o에서 띄워쓰기를 해줘야한다.
    @Query("select count(o) from Order o " + "where o.member.email = :email")

    Long countOrder(@Param("email") String email);
}
