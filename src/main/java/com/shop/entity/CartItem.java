package com.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "cart_item")
public class CartItem extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "cart_item_id")
    private Long id;

    // 지연로딩
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cart_id")
    private Cart cart;            // 다대일 관계로 매핑

    // 지연로딩
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;           // 다대일 관계로 매핑

    private int count;

    // 장바구니에 담을 수량 증가
    public static CartItem createCartItem(Cart cart, Item item, int count){
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setItem(item);
        cartItem.setCount(count);
        return cartItem;
    }

    // 해당 상품을 추가로 장바구니에 담을 때 기존 수량에 더해줌
    public void addCount(int count){
        this.count += count;
    }

    // 장바구니 상품 수량 변경
    public void updateCount(int count){
        this.count = count;
    }

}
