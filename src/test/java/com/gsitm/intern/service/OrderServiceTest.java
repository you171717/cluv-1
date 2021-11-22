package com.gsitm.intern.service;

import com.gsitm.intern.constant.ItemSellStatus;
import com.gsitm.intern.constant.OrderStatus;
import com.gsitm.intern.dto.OrderDto;
import com.gsitm.intern.entity.Item;
import com.gsitm.intern.entity.Member;
import com.gsitm.intern.entity.Order;
import com.gsitm.intern.entity.OrderItem;
import com.gsitm.intern.repository.ItemRepository;
import com.gsitm.intern.repository.MemberRepository;
import com.gsitm.intern.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(properties = { "spring.config.location=classpath:application-test.yml" })
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

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
    @DisplayName("주문 테스트")
    public void order(){
        Item item = saveItem();
        Member member = saveMember();

        OrderDto orderDto = new OrderDto();
        orderDto.setCount(10);
        orderDto.setItemId(item.getId());

        //주문 로직 호출 결과 생성된 주문 번호를 orderId 변수에 저장
        Long orderId = orderService.order(orderDto, member.getEmail());

        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);

        List<OrderItem> orderItems = order.getOrderItems();

        int totalPrice = orderDto.getCount()*item.getPrice();
        //주문 상품의 총 가격과 데이터베이스에 저장된 상품 가격을 비교해서 같으면 성공
        assertEquals(totalPrice, order.getTotalPrice());
    }

    @Test
    @DisplayName("주문 취소 테스트")
    public void cancelOrder(){
        Item item = saveItem();
        Member member = saveMember();

        OrderDto orderDto = new OrderDto();
        orderDto.setCount(10);
        orderDto.setItemId(item.getId());
        Long orderId = orderService.order(orderDto, member.getEmail());

        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        //해당 주문 취소
        orderService.cancelOrder(orderId);

        //주문 상태가 취소 상태면 테스트 통과
        assertEquals(OrderStatus.CANCEL, order.getOrderStatus());
        //취소 후 상품 재고가 처음 재고인 100개면 테스트 통과
        assertEquals(100, item.getStockNumber());
    }
}
