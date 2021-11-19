package com.shop.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@Data // get set 인거 data로 통합함
@ToString
public class Category {

    @Id
    @Column(name = "Cate_Code" , nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cate_code;

    @Column(name = "Cate_Name" , nullable = false , unique = true)
    private String Cate_Name;





}
