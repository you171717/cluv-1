package com.gsitm.intern.dto;

import com.gsitm.intern.constant.OrderStatus;
import com.gsitm.intern.constant.ReturnStatus;
import com.gsitm.intern.entity.Order;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class OrderHistDto {

    public OrderHistDto(Order order){
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm"));
        this.orderStatus = order.getOrderStatus();

		if( order.getReturnReqDate() != null ) {
			this.returnReqDate = order.getReturnReqDate().format(DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm"));
		}

		if( order.getReturnConfirmDate() != null ) {
			this.returnConfirmDate = order.getReturnConfirmDate().format(DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm"));
		}

		this.returnStatus = order.getReturnStatus();
	}

    private Long orderId; // 주문아이디

    private String orderDate; // 주문날짜

    private OrderStatus orderStatus; // 주문상태

	//반품 요청일
	private String returnReqDate;

	//반품 확정일
	private String returnConfirmDate;

	//반품 여부
	private ReturnStatus returnStatus;

    //주문 상품 리스트
    private List<OrderItemDto> orderItemDtoList = new ArrayList<>();

    public void addOrderItemDto(OrderItemDto orderItemDto){
        orderItemDtoList.add(orderItemDto);
    }

}
