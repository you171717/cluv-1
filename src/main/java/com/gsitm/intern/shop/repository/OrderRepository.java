package com.gsitm.intern.shop.repository;

import com.gsitm.intern.shop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
