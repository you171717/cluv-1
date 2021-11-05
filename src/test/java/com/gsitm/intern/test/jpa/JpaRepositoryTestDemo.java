package com.gsitm.intern.test.jpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Description :
 *
 * @author leejinho
 * @version 1.0
 */

@SpringBootTest
class JpaRepositoryTestDemo {

    @Autowired
    private JpaRepositoryTest jpaRepositoryTest;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DisplayName("JPA 저장 테스트")
    @Rollback(value = false)
    public void save() {
        JpaEntity jpaEntity = new JpaEntity("홍길동");
        JpaEntity savedEntity = jpaRepositoryTest.save(jpaEntity);
        Assertions.assertEquals(savedEntity.getName(), "홍길동");
    }

    @Test
    @DisplayName("Query Dsl 테스트")
    public void queryDslTest() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QJpaEntity jpaEntity = QJpaEntity.jpaEntity;

        List<JpaEntity> jpaEntities = queryFactory.selectFrom(jpaEntity)
                .where(jpaEntity.name.eq("홍길동"))
                .fetch();
        Assertions.assertEquals(22, jpaEntities.size());
    }
}