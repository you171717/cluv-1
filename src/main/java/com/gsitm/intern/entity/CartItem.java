package com.gsitm.intern.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name="cart_item")
public class CartItem extends BaseEntity{
    @Id
    @GeneratedValue
    @Column(name="cart_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cart_id")
    private Cart cart;  //하나의 장바구니에 여러 상품

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;  //한 상품은 여러 장바구니의 장바구니 상품으로 담길 수 있음

    private int count;  //같은 상품을 장바구니에 몇개 담을지

    public static CartItem createCartItem(Cart cart, Item item, int count){
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setItem(item);
        cartItem.setCount(count);
        return cartItem;
    }

    //장바구니에 기존에 담겨 있는 상품인데 추가로 장바구니에 담을 때 기존 수량에 현재 담을 수량 더해주는 메소드
    public void addCount(int count){
        this.count += count;
    }

    //현재 장바구니에 담겨있는 수량 변경하는 메소드
    public void updateCount(int count){
        this.count = count;
    }
}
