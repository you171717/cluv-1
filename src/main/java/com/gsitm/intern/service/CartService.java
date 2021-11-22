package com.gsitm.intern.service;

import com.gsitm.intern.dto.CartDetailDto;
import com.gsitm.intern.dto.CartItemDto;
import com.gsitm.intern.dto.CartOrderDto;
import com.gsitm.intern.dto.OrderDto;
import com.gsitm.intern.entity.Cart;
import com.gsitm.intern.entity.CartItem;
import com.gsitm.intern.entity.Item;
import com.gsitm.intern.entity.Member;
import com.gsitm.intern.repository.CartItemRepository;
import com.gsitm.intern.repository.CartRepository;
import com.gsitm.intern.repository.ItemRepository;
import com.gsitm.intern.repository.MemberRepository;
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

    public Long addCart(CartItemDto cartItemDto, String email){

        //장바구니에 담을 상품 엔티티 조회
        Item item = itemRepository.findById(cartItemDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);
        //현재 로그인한 사용자 엔티티 조회
        Member member = memberRepository.findByEmail(email);

        //현재 로그인한 사용자의 장바구니 엔티티 조회
        Cart cart = cartRepository.findByMemberId(member.getId());
        //상품을 처음으로 장바구니에 담은 경우 해당 사용자의 장바구니 엔티티 생성
        if(cart == null){
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        //현재 상품이 장바구니에 이미 들어가 있는지 조회
        CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());

        if(savedCartItem != null){
            savedCartItem.addCount(cartItemDto.getCount());
            return savedCartItem.getId();
        } else{
            //장바구니 엔티티, 상품 엔티티, 장바구니에 담을 수량을 이용해서 CartItem 엔티티 생성
            CartItem cartItem = CartItem.createCartItem(cart, item, cartItemDto.getCount());
            cartItemRepository.save(cartItem);
            return cartItem.getId();

        }
    }
    @Transactional(readOnly = true)
    public List<CartDetailDto> getCartList(String email){

        List<CartDetailDto> cartDetailDtoList = new ArrayList<>();

        Member member = memberRepository.findByEmail(email);
        //현재 로그인한 사용자 장바구니 엔티티 조회
        Cart cart = cartRepository.findByMemberId(member.getId());
        //장바구니에 상품 한 번도 안 담은 경우 장바구니 엔티티가 없으므로 빈 리스트 ㅏㄴ환
        if(cart == null){
            return cartDetailDtoList;
        }
        //장바구니에 담겨있는 상품 정보 조회
        cartDetailDtoList = cartItemRepository.findCartDetailDtoList(cart.getId());

        return cartDetailDtoList;
    }

    @Transactional(readOnly = true)
    public boolean validateCartItem(Long cartItemId, String email){
        //현재 로그인한 사용자 조회
        Member curMember = memberRepository.findByEmail(email);
        //장바구니 상품을 저장한 사용자 조회
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        Member saveMember = cartItem.getCart().getMember();

        //현재 로그인한 사용자와 상품 저장한 사용자 비교
        if(!StringUtils.equals(curMember.getEmail(), saveMember.getEmail())){
            return false;
        }
        return true;
    }

    //장바구니 상품의 수량 업데이트하는 메소드
    public void updateCartItemCount(Long cartItemId, int count){
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        cartItem.updateCount(count);
    }

    //장바구니 상품 삭제 메소드
    public void deleteCartItem(Long cartItemId){
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        cartItemRepository.delete(cartItem);
    }

    public Long orderCartItem(List<CartOrderDto> cartOrderDtoList, String email){
        List<OrderDto> orderDtoList = new ArrayList<>();
        //장바구니 페이지에서 전달받은 주문 상품 번호를 이용해 주문 로직으로 전달할 orderDto 객체 생성
        for(CartOrderDto cartOrderDto : cartOrderDtoList){
            CartItem cartItem = cartItemRepository
                    .findById(cartOrderDto.getCartItemId())
                    .orElseThrow(EntityNotFoundException::new);

            OrderDto orderDto = new OrderDto();
            orderDto.setItemId(cartItem.getItem().getId());
            orderDto.setCount(cartItem.getCount());
            orderDtoList.add(orderDto);
        }

        //장바구니에 담은 상품을 주문하도록 주문 로직 호출
        Long orderId = orderService.orders(orderDtoList, email);

        //주문한 상품들을 장바구니에서 제거
        for(CartOrderDto cartOrderDto : cartOrderDtoList){
            CartItem cartItem = cartItemRepository
                    .findById(cartOrderDto.getCartItemId())
                    .orElseThrow(EntityNotFoundException::new);
            cartItemRepository.delete(cartItem);
        }
        return orderId;
    }
}

