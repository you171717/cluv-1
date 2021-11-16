package com.gsitm.intern.test.jpa;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Repository
public interface TestMemberRepository extends JpaRepository<JpaEntity, JpaCompositeKey> {
    List<JpaEntity> findAll();
}
