package com.gsitm.intern.study.repository;

import com.gsitm.intern.study.entity.Employee;
import com.gsitm.intern.study.entity.EmployeeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, EmployeeId> { // 제네릭 타입: <엔티티 클래스, 엔티티 클래스의 기본키>
    Employee findByEmployeeId(EmployeeId employeeId);
}