package com.shop.service;

import com.shop.dto.OrderDto;
import com.shop.entity.*;
import com.shop.repository.*;
import com.shop.dto.OrderHistDto;
import com.shop.dto.OrderItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import org.thymeleaf.util.StringUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;

    private final MemberRepository memberRepository;

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final ItemImgRepository itemImgRepository;

    public Long order(OrderDto orderDto, String email){

        Item item = itemRepository.findById(orderDto.getItemId())
                .orElseThrow(EntityNotFoundException::new); // 주문할 상품을 조회

        Member member = memberRepository.findByEmail(email);
        // 현재 로그인한 회원의 이메일 정보를 이용해서 회원 정보 조회
        member.setPoint(member.getPoint() - orderDto.getInput_point() + (int) (orderDto.getAmount_price() * 0.01));

        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount(), orderDto.getInput_point(), orderDto.getAmount_price());
        // 주문할 상품 엔티티와 주문 수량을 이용하여 주문 상품 엔티티 생성
        orderItemList.add(orderItem);
        Order order = Order.createOrder(member, orderItemList);
        // 회원 정보와 주문할 상품 리스트 정보를 이용하여 주문 엔티티 생성
        memberRepository.save(member);
        orderRepository.save(order);
        // 생성한 주문 엔티티 저장

        return order.getId();
    }

    @Transactional(readOnly = true)
    public Page<OrderHistDto> getOrderList(String email, Pageable pageable) {

        List<Order> orders = orderRepository.findOrders(email, pageable);
        // 유저의 아이디와 페이징 조건을 이용하여 주문 목록을 조회
        Long totalCount = orderRepository.countOrder(email);
        // 유저의 주문 총 개수를 구한다.

        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        for (Order order : orders) {
            OrderHistDto orderHistDto = new OrderHistDto(order);
            List<OrderItem> orderItems = order.getOrderItems();
            for (OrderItem orderItem : orderItems) {
                ItemImg itemImg = itemImgRepository.findByItemIdAndRepimgYn
                        (orderItem.getItem().getId(), "Y"); // 주문한 상품의 대표 이미지
                OrderItemDto orderItemDto =
                        new OrderItemDto(orderItem, itemImg.getImgUrl());
                orderHistDto.addOrderItemDto(orderItemDto);
            }

            orderHistDtos.add(orderHistDto);
        }
        // 주문 리스트를 순회하면서 구매 이력 페이지에 전달할 DTO를 생성

        return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount); // 페이지 구현 객체를 생성
    }

    @Transactional(readOnly = true)
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
        order.cancelOrder();
    }

    public Long orders(List<OrderDto> orderDtoList, String email){

        Member member = memberRepository.findByEmail(email);
        List<OrderItem> orderItemList = new ArrayList<>();

        for (OrderDto orderDto : orderDtoList) {
            Item item = itemRepository.findById(orderDto.getItemId())
                    .orElseThrow(EntityNotFoundException::new);

            OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount(), orderDto.getInput_point(), orderDto.getAmount_price());
            orderItemList.add(orderItem);
        }

        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);

        return order.getId();
    }

}