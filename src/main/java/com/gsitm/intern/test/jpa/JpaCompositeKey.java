package com.gsitm.intern.test.jpa;


import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
public class JpaCompositeKey implements Serializable {

    private Long id;
    private String phone;

}
