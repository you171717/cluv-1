package com.gsitm.intern.repository;

import com.gsitm.intern.dto.CartDetailDto;
import com.gsitm.intern.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

    @Query("select new com.gsitm.intern.dto.CartDetailDto(ci.id, i.itemNm, i.price, ci.count, im.imgUrl, d.discountDate, d.discountTime, d.discountRate) " +
            "from CartItem ci, ItemImg im " +
            "join ci.item i " +
            "left join i.itemDiscount d " +
            "where ci.cart.id = :cartId " +
            "and im.item.id = ci.item.id " +
            "and im.repImgYn = 'Y' " +
            "order by ci.regTime desc")
    List<CartDetailDto> findCartDetailDtoList(@Param("cartId") Long cartId);
}
