package com.gsitm.intern.repository;

import com.gsitm.intern.dto.ItemDiscountPopupDto;
import com.gsitm.intern.entity.Item;
import com.gsitm.intern.entity.ItemDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemDiscountRepository extends JpaRepository<ItemDiscount, Long>, ItemRepositoryCustom{
}
