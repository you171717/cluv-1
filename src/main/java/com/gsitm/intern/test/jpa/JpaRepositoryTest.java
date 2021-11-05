package com.gsitm.intern.test.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Description :
 *
 * @author leejinho
 * @version 1.0
 */

@Repository
public interface JpaRepositoryTest extends JpaRepository<JpaEntity, Long>, QuerydslPredicateExecutor<JpaEntity> {
}
