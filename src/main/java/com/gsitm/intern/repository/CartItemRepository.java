package com.gsitm.intern.repository;

import com.gsitm.intern.dto.CartDetailDto;
import com.gsitm.intern.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

    //CartDetailDto의 생성자를 이용해 DTO를 반환할 때는 아래처럼 new 키워드와 해당 DTO의 패키지, 클래스명을 적어줌
    //생성자의 파라미터 순서는 DTO 클래스에 명시한 순으로 넣어줘야됨
    @Query("select new com.gsitm.intern.dto.CartDetailDto(ci.id, i.itemNm, i.price, ci.count, im.imgUrl) " +
            "from CartItem ci, ItemImg im " +
            "join ci.item i " +
            "where ci.cart.id = :cartId " +
            "and im.item.id = ci.item.id " +
            "and im.repImgYn = 'Y' " +
            "order by ci.regTime desc"
    )
    List<CartDetailDto> findCartDetailDtoList(Long cartId);
}
