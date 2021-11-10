package com.gsitm.intern.study.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class EmployeeId implements Serializable {
    @Column(name = "employee_id")
    private String employeeId;

    private String name;
}
