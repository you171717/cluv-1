package com.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Category{

    @Id
    @Column(name="cateCode")
    private Long cateCode;

    @Column(unique = true)
    private String cateName;

}
