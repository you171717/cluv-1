package com.gsitm.intern.study.repository;

import com.gsitm.intern.study.entity.Student;
import com.gsitm.intern.study.entity.StudentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, StudentId> { // 제네릭 타입: <엔티티 클래스, 엔티티 클래스의 기본키>
    Student findByStudentId(StudentId studentId);
}
