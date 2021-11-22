package com.shop.service;

import com.shop.dto.CartDetailDto;
import com.shop.dto.CartItemDto;
import com.shop.dto.CartOrderDto;
import com.shop.dto.OrderDto;
import com.shop.entity.Cart;
import com.shop.entity.CartItem;
import com.shop.entity.Item;
import com.shop.entity.Member;
import com.shop.repository.CartItemRepository;
import com.shop.repository.CartRepository;
import com.shop.repository.ItemRepository;
import com.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderService orderService;

    public Long addCart(CartItemDto cartItemDto, String email) {
        Item item = itemRepository.findById(cartItemDto.getItemId())  // 상품 엔티티 조회
                .orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email);          // 현재 로그인한 회원 엔티티 조회

        // 현재 로그인한 회원의 장바구니 엔티티 조회
        Cart cart = cartRepository.findByMemberId(member.getId());
        if (cart == null) {
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        // 상품을 처음으로 장바구니에 담을 경우 해당회원 장바구니 엔티티 생성
        CartItem savedCartItem =
                cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());


        if (savedCartItem != null) {
            savedCartItem.addCount(cartItemDto.getCount());       // 기존 수량에 담을 수량을 더해줌
            return savedCartItem.getId();
        } else {
            CartItem cartItem =
                    CartItem.createCartItem(cart, item, cartItemDto.getCount());    // CartItem 엔티티 생성
            cartItemRepository.save(cartItem);     // 장바구니에 들어갈 상품 저장
            return cartItem.getId();
        }
    }
        
        @Transactional(readOnly = true)
        public List<CartDetailDto> getCartList(String email){

            List<CartDetailDto> cartDetailDtoList = new ArrayList<>();

            Member member = memberRepository.findByEmail(email);
            Cart cart = cartRepository.findByMemberId(member.getId());
            if(cart == null){
                return cartDetailDtoList;
            }

            cartDetailDtoList = cartItemRepository.findCartDetailDtoList(cart.getId());

            return cartDetailDtoList;
    }

    // 현재 로그인한 회원, 장바구니 담은 회원과 같은 확인
    @Transactional(readOnly = true)
    public boolean validateCartItem(Long cartItemId, String email){
        Member curMember = memberRepository.findByEmail(email);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        Member savedMember = cartItem.getCart().getMember();

        if(!StringUtils.equals(curMember.getEmail(),
                savedMember.getEmail())){
            return false;
        }
        return true;
    }

    // 장바구니 상품의 수량 업데이트 메소드
    public void updateCartItemCount(Long cartItemId, int count){
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);

        cartItem.updateCount(count);
    }

    public void deleteCartItem(Long cartItemId){
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        cartItemRepository.delete(cartItem);
    }

    public Long orderCartItem(List<CartOrderDto> cartOrderDtoList, String email){
        List<OrderDto> orderDtoList = new ArrayList<>();
        // 주문 상품 번호를 이용해 주문 로직으로 전달할 orderDto 객체 생성
        for(CartOrderDto cartOrderDto : cartOrderDtoList){
            CartItem cartItem = cartItemRepository
                    .findById(cartOrderDto.getCartItemId())
                    .orElseThrow(EntityNotFoundException::new);

            OrderDto orderDto = new OrderDto();
            orderDto.setItemId(cartItem.getItem().getId());
            orderDto.setCount(cartItem.getCount());
            orderDtoList.add(orderDto);
        }

        // 주문 로직 호출
        Long orderId = orderService.orders(orderDtoList, email);

        // 주문한 상품들을 장바구니에서 제거
        for(CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartItem cartItem = cartItemRepository
                    .findById(cartOrderDto.getCartItemId())
                    .orElseThrow(EntityNotFoundException::new);
            cartItemRepository.delete(cartItem);
        }
        return orderId;

    }
}