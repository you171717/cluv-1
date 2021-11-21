package com.gsitm.intern.service;

import com.gsitm.intern.constant.ItemSellStatus;
import com.gsitm.intern.dto.CartItemDto;
import com.gsitm.intern.entity.CartItem;
import com.gsitm.intern.entity.Item;
import com.gsitm.intern.entity.Member;
import com.gsitm.intern.repository.CartItemRepository;
import com.gsitm.intern.repository.ItemRepository;
import com.gsitm.intern.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(properties = { "spring.config.location=classpath:application-test.yml"})

public class CartServiceTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CartService cartService;

    @Autowired
    CartItemRepository cartItemRepository;

    public Item saveItem(){
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        return itemRepository.save(item);
    }

    public Member saveMember(){
        Member member = new Member();
        member.setEmail("test@test.com");
        return memberRepository.save(member);
    }

    @Test
    @DisplayName("장바구니 담기 테스트")
    public void addCart(){
        Item item = saveItem();
        Member member = saveMember();

        CartItemDto cartItemDto = new CartItemDto();
        //장바구니에 담을 상품, 수량을 cartItemDto 객체에 세팅
        cartItemDto.setCount(5);
        cartItemDto.setItemId(item.getId());

        //상품을 장바구니에 담는 로직 호출 결과 생성된 장바구니 상품 아이디를 cartItemId 변수에 저장
        Long cartItemId = cartService.addCart(cartItemDto, member.getEmail());

        //장바구니 상품 아이디를 이용해 생성된 상품 정보 조회
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityExistsException::new);

        //상품 아이디와 장바구니에 저장된 상품 아이디 같으면 테스트 통과
        assertEquals(item.getId(), cartItem.getItem().getId());
        //장바구니에 담았던 수량과 실제 장바구니에 저장된 수량 같으면 테스트 통과
        assertEquals(cartItemDto.getCount(), cartItem.getCount());
    }
}
