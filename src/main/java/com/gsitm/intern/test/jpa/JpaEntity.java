package com.gsitm.intern.test.jpa;

import lombok.*;

import javax.persistence.*;

import java.io.Serializable;

/**
 * Description :
 *
 * @author leejinho
 * @version 1.0
 */

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(JpaCompositeKey.class)
public class JpaEntity implements Serializable {
    @Id
    private Long id;
    @Id
    private String phone;

    private String name;

    @Builder
    public JpaEntity(Long id, String phone, String name) {
        this.id = id;
        this.phone = phone;
        this.name = name;
    }
}
