package com.gsitm.intern.test.jpa;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Description :
 *
 * @author leejinho
 * @version 1.0
 */

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JpaEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;

    public JpaEntity(String name) {
        this.name = name;
    }
}
