package com.shop.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;


@Entity
@Table(name = "categoryRef")
@Data // get set 인거 data로 통합함
@ToString
public class CategoryRef {

    @Id
    @Column(name = "Cate_CodeRef" , nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cate_codeRef;

    @Column(name = "Cate_NameRef" , nullable = false , unique = true)
    private String Cate_NameRef;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Cate_Code")
    private Category category;
}
