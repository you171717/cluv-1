package com.gsitm.intern.repository;

import com.gsitm.intern.constant.DiscountTime;
import com.gsitm.intern.constant.ItemSellStatus;
import com.gsitm.intern.dto.*;
import com.gsitm.intern.entity.*;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public ItemRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus) {
        return searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus);
    }

    private BooleanExpression regDtsAfter(String searchDateType) {
        LocalDateTime dateTime = LocalDateTime.now();

        if(StringUtils.equals("all", searchDateType) || searchDateType == null) {
            return null;
        } else if(StringUtils.equals("1d", searchDateType)) {
            dateTime = dateTime.minusDays(1);
        } else if(StringUtils.equals("1w", searchDateType)) {
            dateTime = dateTime.minusWeeks(1);
        } else if(StringUtils.equals("1m", searchDateType)) {
            dateTime = dateTime.minusMonths(1);
        } else if(StringUtils.equals("6m", searchDateType)) {
            dateTime = dateTime.minusMonths(6);
        }

        return QItem.item.regTime.after(dateTime);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {

        if(StringUtils.equals("itemNm", searchBy)) {
            return QItem.item.itemNm.like("%" + searchQuery + "%");
        } else if(StringUtils.equals("createdBy", searchBy)) {
            return QItem.item.createdBy.like("%" + searchQuery + "%");
        }

        return null;
    }

    @Override
    public Page<ItemMngDto> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {

        QItem item = QItem.item;
        QItemDiscount itemDiscount = QItemDiscount.itemDiscount;

        QueryResults<ItemMngDto> results = queryFactory.select(new QItemMngDto(item.id, item.itemNm, item.price, item.itemDetail, item.itemSellStatus, item.stockNumber,item.regTime, item.updateTime, item.createdBy, item.modifiedBy, itemDiscount.id, itemDiscount.discountDate, itemDiscount.discountTime, itemDiscount.discountRate))
                                                       .from(item)
                                                       .leftJoin(item.itemDiscount, itemDiscount)
                                                       .where(regDtsAfter((itemSearchDto.getSearchDateType())),
                                                              searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                                                              searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))
                                                       .orderBy(item.id.desc())
                                                       .offset(pageable.getOffset())
                                                       .limit(pageable.getPageSize())
                                                       .fetchResults();

        List<ItemMngDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }


    private BooleanExpression itemNmLike(String searchQuery) {
        return StringUtils.isEmpty(searchQuery) ? null : QItem.item.itemNm.like("%" + searchQuery + "%");
    }

    @Override
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {

        QItem item = QItem.item;
        QItemImg itemImg = QItemImg.itemImg;

        QueryResults<MainItemDto> results = queryFactory.select(new QMainItemDto(item.id, item.itemNm, item.itemDetail, itemImg.imgUrl, item.price))
                                                        .from(itemImg)
                                                        .join(itemImg.item, item)
                                                        .where(itemImg.repImgYn.eq("Y"))
                                                        .where(itemNmLike(itemSearchDto.getSearchQuery()))
                                                        .orderBy(item.id.desc())
                                                        .offset(pageable.getOffset())
                                                        .limit(pageable.getPageSize())
                                                        .fetchResults();

        List<MainItemDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<ItemDiscountDto> getDiscountItemPage(Pageable pageable) {

        QItem item = QItem.item;
        QItemImg itemImg = QItemImg.itemImg;
        QItemDiscount itemDiscount = QItemDiscount.itemDiscount;

        String discountTime = "";
        switch (LocalTime.now().getHour()) {
            case 0: case 1: case 2: case 3: discountTime = "ONE"; break;
            case 4: case 5: case 6: case 7: discountTime = "TWO"; break;
            case 8: case 9: case 10: case 11: discountTime = "THREE"; break;
            case 12: case 13: case 14: case 15: discountTime = "FOUR"; break;
            case 16: case 17: case 18: case 19: discountTime = "FIVE"; break;
            case 20: case 21: case 22: case 23: discountTime = "SIX"; break;
        }

        QueryResults<ItemDiscountDto> results = queryFactory.select(new QItemDiscountDto(item.id, item.itemNm, item.itemDetail, itemImg.imgUrl, item.price, itemDiscount.discountRate))
                                                        .from(itemDiscount)
                                                        .innerJoin(itemDiscount.item, item)
                                                        .innerJoin(itemImg).on(itemDiscount.item.id.eq(itemImg.item.id))
                                                        .where(itemImg.repImgYn.eq("Y"))
                                                        .where(itemDiscount.discountDate.eq(LocalDate.now()))
                                                        .where(itemDiscount.discountTime.eq(DiscountTime.valueOf(discountTime)))
                                                        .orderBy(item.id.desc())
                                                        .offset(pageable.getOffset())
                                                        .limit(pageable.getPageSize())
                                                        .fetchResults();

        List<ItemDiscountDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public ItemDiscountPopupDto getItemDiscountPopup (Long id) {
        QItemDiscount itemDiscount = QItemDiscount.itemDiscount;

        QueryResults<ItemDiscountPopupDto> results = queryFactory.select(new QItemDiscountPopupDto(itemDiscount.id, itemDiscount.item.id, itemDiscount.discountDate, itemDiscount.discountTime, itemDiscount.discountRate))
                .from(itemDiscount)
                .where(itemDiscount.item.id.eq(id))
                .fetchResults();

        if(results.getTotal() > 0)
            return results.getResults().get(0);
        else
            return null;
    }

    @Override
    public ItemDiscount getItemDiscount(Long id) {
        QueryResults<ItemDiscount> results = queryFactory.selectFrom(QItemDiscount.itemDiscount)
                                                         .where(QItemDiscount.itemDiscount.id.eq(id))
                                                         .fetchResults();
        if(results.getTotal() > 0)
            return results.getResults().get(0);
        else
            return null;
    }

    @Override
    public Item getItem(Long id) {
        QueryResults<Item> results = queryFactory.selectFrom(QItem.item)
                                                 .where(QItem.item.id.eq(id))
                                                 .fetchResults();
        if(results.getTotal() > 0)
            return results.getResults().get(0);
        else
            return null;
    }

    @Override
    public ItemMngDto getDiscountItemDtl(Long id) {
        QItem item = QItem.item;
        QItemDiscount itemDiscount = QItemDiscount.itemDiscount;

        QueryResults<ItemMngDto> results = queryFactory.select(new QItemMngDto(item.id, item.itemNm, item.price, item.itemDetail, item.itemSellStatus, item.stockNumber,item.regTime, item.updateTime, item.createdBy, item.modifiedBy, itemDiscount.id, itemDiscount.discountDate, itemDiscount.discountTime, itemDiscount.discountRate))
                                                       .from(item)
                                                       .leftJoin(item.itemDiscount, itemDiscount)
                                                       .where(item.id.eq(id))
                                                       .fetchResults();

        if(results.getTotal() > 0)
            return results.getResults().get(0);
        else
            return null;
    }
}
