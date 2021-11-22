package com.gsitm.intern.repository;

import com.gsitm.intern.dto.ItemSearchDto;
import com.gsitm.intern.dto.MainItemDto;
import com.gsitm.intern.dto.QMainItemDto;
import com.gsitm.intern.entity.QItem;
import com.gsitm.intern.entity.QItemDiscount;
import com.gsitm.intern.entity.QItemImg;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemRepositoryCustomImplTest {
    @PersistenceContext
    EntityManager em;

    private JPAQueryFactory queryFactory;

    private BooleanExpression itemNmLike(String searchQuery) {
        return StringUtils.isEmpty(searchQuery) ? null : QItem.item.itemNm.like("%" + searchQuery + "%");
    }


    @Test
    public void getDiscountItemPage() {

        QItem item = QItem.item;
        QItemImg itemImg = QItemImg.itemImg;
        QItemDiscount itemDiscount = QItemDiscount.itemDiscount;
        Pageable pageable = PageRequest.of(0, 6);

        this.queryFactory = new JPAQueryFactory(em);
        System.out.println("0");
        QueryResults<MainItemDto> results = queryFactory.select(new QMainItemDto(item.id, item.itemNm, item.itemDetail, itemImg.imgUrl, item.price))
                .from(itemImg, itemDiscount)
                .join(itemDiscount.item, item)
                .join(itemImg.item, item)
                .where(itemImg.repImgYn.eq("Y"))
                //.where(itemNmLike(itemSearchDto.getSearchQuery()))
                .orderBy(item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();


        System.out.println("1");
        List<MainItemDto> content = results.getResults();
        System.out.println("2");
        long total = results.getTotal();
        System.out.println("3");

        assertEquals("1", "1");
    }
}