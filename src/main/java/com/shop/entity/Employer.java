package com.shop.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "employer")
@IdClass(EmployerId.class)
public class Employer {

    @Id
    private int emprNo;

    @Id
    private String emprName;

    private String phone;

}
