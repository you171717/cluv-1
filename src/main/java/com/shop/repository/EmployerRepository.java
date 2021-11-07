package com.shop.repository;

import com.shop.entity.Employer;
import com.shop.entity.EmployerId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployerRepository extends JpaRepository<Employer, EmployerId> {

}
