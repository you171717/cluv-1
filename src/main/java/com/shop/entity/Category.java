package com.shop.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@Getter
@Setter
@ToString
public class Category {

//    @Id
//    @Column(name = "Cate_Code" , nullable = false)
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long cate_code;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Cate_Name" , nullable = false , unique = true)
    private String Cate_Name;

    @Column(name = "Cate_First" , nullable = false)
    private String Cate_First;

    @Column(name = "Cate_Sec" , nullable = false)
    private String Cate_Sec;


}
