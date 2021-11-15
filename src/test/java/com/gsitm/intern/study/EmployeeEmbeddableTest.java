package com.gsitm.intern.study;

import com.gsitm.intern.study.entity.Employee;
import com.gsitm.intern.study.entity.EmployeeId;
import com.gsitm.intern.study.repository.EmployeeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = { "spring.config.location=classpath:application-test.yml" })
public class EmployeeEmbeddableTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    @DisplayName("Embeddable 테스트")
    public void EmbeddableTest() {
        EmployeeId employeeId = new EmployeeId();
        employeeId.setEmployeeId("1663");
        employeeId.setName("wj");

        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        employee.setPhone("010-0000-0000");

        employeeRepository.save(employee);

        employee = employeeRepository.findByEmployeeId(employeeId);

        System.out.println(employee.toString());
    }
}