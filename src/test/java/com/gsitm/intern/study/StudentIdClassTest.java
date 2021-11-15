package com.gsitm.intern.study;

import com.gsitm.intern.study.entity.Student;
import com.gsitm.intern.study.entity.StudentId;
import com.gsitm.intern.study.repository.StudentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StudentIdClassTest {

    @Autowired
    StudentRepository studentRepository;

    @Test
    @DisplayName("@IdClass 테스트")
    public void IdClassTest() {


        Student student = new Student();
        student.setStudentId("333");
        student.setName("wj");
        student.setScore(100);

        studentRepository.save(student);

        StudentId studentId = new StudentId();
        studentId.setStudentId("333");
        studentId.setName("wj");

        student = studentRepository.findById(studentId).get();

        System.out.println(student.toString());
    }
}
