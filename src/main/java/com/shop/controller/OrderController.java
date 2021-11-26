package com.shop.controller;

import com.shop.dto.OrderDto;
import com.shop.dto.OrderHistDto;
import com.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    OrderService orderService;


    @PostMapping(value = "/order")
    public @ResponseBody
    ResponseEntity order(@RequestBody @Valid OrderDto orderDto,
                         BindingResult bindingResult, Principal principal) {       //비동기 처리
        if (bindingResult.hasErrors()) {  //주문 정보를 받는 orderDto 객체에 데이터 바인딩시 에러가 있는지 검사
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(),
                    HttpStatus.BAD_REQUEST);        //에러 정보를 ResponseEntity 객체에 데이터 바인딩 시 에러가 있는지 검사
        }
        String email = principal.getName();     //현재 로그인한 회원의 이메일 정보를 조회
        Long orderId;

        try {
            orderId = orderService.order(orderDto, email);      //화면으로 넘어오는 주문 정보와 회원의 이메일 정보를 이용해 주문 로직을 호출
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);        //결과값으로 생성된 주문 번호와 요청이 성공했다는 응답 상태 코드 반환
    }

    @GetMapping(value = {"/orders", "/orders/{page}"})
    public String orderHist(@PathVariable("page") Optional<Integer> page,
                            Principal principal, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4); //한번에 갖고 올 주문의 개수 4개

        Page<OrderHistDto> orderHistDtoList =
                orderService.getOrderList(principal.getName(), pageable);   //현재 로그인한 회원은 이메일과 페이징 객체를 파라미터로 전달해
        //화면에 전달한 주문 목룍 데이터를 리턴 값으로 받음
        model.addAttribute("orders", orderHistDtoList);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);
        return "order/orderHist";
    }

    @PostMapping(value = "/order/{orderId}/cancel")
    public @ResponseBody
    ResponseEntity cancelOrder
            (@PathVariable("orderId") Long orderId, Principal principal) {

        //자바 스크립트에서 취소할 주문 번호는 조작이 가능해 다른 사용자의 주문을 취소하지 못하게 주문취소 권한 검사
        if (!orderService.validateOrder(orderId, principal.getName())) {
            return new ResponseEntity<String>("주문 취소 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        orderService.cancelOrder(orderId);      //주문 취소 로직 호출
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }
}