package com.gsitm.intern.test.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QJpaEntity is a Querydsl query type for JpaEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QJpaEntity extends EntityPathBase<JpaEntity> {

    private static final long serialVersionUID = -290734842L;

    public static final QJpaEntity jpaEntity = new QJpaEntity("jpaEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QJpaEntity(String variable) {
        super(JpaEntity.class, forVariable(variable));
    }

    public QJpaEntity(Path<? extends JpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QJpaEntity(PathMetadata metadata) {
        super(JpaEntity.class, metadata);
    }

}

