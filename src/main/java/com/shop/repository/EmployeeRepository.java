package com.shop.repository;

import com.shop.entity.Employee;
import com.shop.entity.EmployeeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, EmployeeId> {

    Employee findByEmployeeId(EmployeeId employeeId);

}
