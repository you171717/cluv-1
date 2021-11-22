package com.study.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

//  @Fetch(FetchMode.SUBSELECT)
//  @BatchSize(size = 5)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner")
    private List<Cat> cats = new ArrayList<>();

}
