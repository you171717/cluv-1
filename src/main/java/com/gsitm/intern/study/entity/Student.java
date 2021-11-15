package com.gsitm.intern.study.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student")
@IdClass(StudentId.class)
public class Student {
    @Id
    @Column(name = "student_id")
    private String studentId;

    @Id
    private String name;

    @Column(name = "school_id")
    private int schoolId;
    private int score;
}
