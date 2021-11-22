package com.gsitm.intern.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "cart_item")
public class CartItem extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "cart_item_id")
    private Long id;
    //  fetch = FetchType.LAZY 방식으로 설정 (지연로딩방식) 2주차 과제에 포함
    //  실무에서는 매핑 되는 엔티티 개수가 많기 때문에 쿼리의 실행방식을 예측할 수 없다
    //  그래서 지연로딩방식을 사용한다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    //  fetch = FetchType.LAZY 방식으로 설정 (지연로딩방식) 2주차 과제에 포함
    //  실무에서는 매핑 되는 엔티티 개수가 많기 때문에 쿼리의 실행방식을 예측할 수 없다
    //  그래서 지연로딩방식을 사용한다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
    
    private int count;

    public static CartItem createCartItem(Cart cart, Item item, int count){
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setItem(item);
        cartItem.setCount(count);
        return cartItem;
    }

    public void addCount(int count){
        this.count += count;
    }

    public void updateCount(int count){ this.count = count; }
}
