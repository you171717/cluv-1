package com.gsitm.intern.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItemDiscount is a Querydsl query type for ItemDiscount
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QItemDiscount extends EntityPathBase<ItemDiscount> {

    private static final long serialVersionUID = 1792625768L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QItemDiscount itemDiscount = new QItemDiscount("itemDiscount");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final DatePath<java.time.LocalDate> discountDate = createDate("discountDate", java.time.LocalDate.class);

    public final NumberPath<Integer> discountRate = createNumber("discountRate", Integer.class);

    public final EnumPath<com.gsitm.intern.constant.DiscountTime> discountTime = createEnum("discountTime", com.gsitm.intern.constant.DiscountTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QItem item;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public QItemDiscount(String variable) {
        this(ItemDiscount.class, forVariable(variable), INITS);
    }

    public QItemDiscount(Path<? extends ItemDiscount> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QItemDiscount(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QItemDiscount(PathMetadata metadata, PathInits inits) {
        this(ItemDiscount.class, metadata, inits);
    }

    public QItemDiscount(Class<? extends ItemDiscount> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item"), inits.get("item")) : null;
    }

}

