package com.gsitm.intern.service;

import com.gsitm.intern.dto.OrderDto;
import com.gsitm.intern.dto.OrderHistDto;
import com.gsitm.intern.dto.OrderItemDto;
import com.gsitm.intern.entity.*;
import com.gsitm.intern.repository.ItemImgRepository;
import com.gsitm.intern.repository.ItemRepository;
import com.gsitm.intern.repository.MemberRepository;
import com.gsitm.intern.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemImgRepository itemImgRepository;

    public Long order(OrderDto orderDto, String email){
        //주문할 상품 조회
        Item item = itemRepository.findById(orderDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email);

        List<OrderItem> orderItemList = new ArrayList<>();
        //주문할 상품 엔티티와 주문 수량을 이용해 주문상품 엔티티 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        orderItemList.add(orderItem);

        //회원 정보와 주문할 상품 리스트 정보를 이용하여 주문 엔티티 생성
        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);

        return order.getId();
    }

    @Transactional(readOnly = true)
    public Page<OrderHistDto> getOrderList(String email, Pageable pageable) {

        //사용자 이메일과 페이징 조건을 이용하여 주문 목록 조회
        List<Order> orders = orderRepository.findOrders(email, pageable);
        //사용자의 주문 총 개수
        Long totalCount = orderRepository.countOrder(email);

        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        //주문 리스트를 순회하며 구매 이력 페이지에 전달할 DTO 생성
        for (Order order : orders) {
            OrderHistDto orderHistDto = new OrderHistDto(order);
            List<OrderItem> orderItems = order.getOrderItems();
            for (OrderItem orderItem : orderItems) {
                //주문 상품의 대표 이미지 조회
                ItemImg itemImg = itemImgRepository.findByItemIdAndRepImgYn(
                        orderItem.getItem().getId(), "Y");
                OrderItemDto orderItemDto = new OrderItemDto(orderItem, itemImg.getImgUrl());
                orderHistDto.addOrderItemDto(orderItemDto);
            }

            orderHistDtos.add(orderHistDto);
        }

        //페이지 구현 객체를 생성해서 반환
        return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount);
    }

    @Transactional(readOnly = true)
    //로그인한 사용자와 주문 데이터를 생성한 사용자가 똑같은지 검사
    public boolean validateOrder(Long orderId, String email){
        Member curMember = memberRepository.findByEmail(email);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        Member savedMember = order.getMember();

        if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())){
            return false;
        }

        return true;
    }

    public void cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        //주문 취소 상태로 변경하면 변경 감지 기능에 의해 트랜잭션이 끝날 때 update 쿼리 실행됨
        order.cancelOrder();
    }
}
