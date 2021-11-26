package com.shop.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.constant.ItemSellStatus;
import com.shop.dto.ItemSearchDto;
import com.shop.dto.MainItemDto;
import com.shop.dto.QMainItemDto;
import com.shop.entity.Item;
import com.shop.entity.QItem;
import com.shop.entity.QItemImg;
import com.shop.entity.QOrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public ItemRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus){
        return searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus);
    }

    private BooleanExpression regDtsAfter(String searchDateType){

        LocalDateTime dateTime = LocalDateTime.now();

        if(StringUtils.equals("all", searchDateType) || searchDateType == null){
            return null;
        } else if(StringUtils.equals("1d", searchDateType)){
            dateTime = dateTime.minusDays(1);
        } else if(StringUtils.equals("1w", searchDateType)){
            dateTime = dateTime.minusWeeks(1);
        } else if(StringUtils.equals("1m", searchDateType)){
            dateTime = dateTime.minusMonths(1);
        } else if(StringUtils.equals("6m", searchDateType)){
            dateTime = dateTime.minusMonths(6);
        }

        return QItem.item.regTime.after(dateTime);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery){

        if(StringUtils.equals("itemNm", searchBy)){
            return QItem.item.itemNm.like("%" + searchQuery + "%");
        } else if(StringUtils.equals("createdBy", searchBy)){
            return QItem.item.createdBy.like("%" + searchQuery + "%");
        }

        return null;
    }

    @Override
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {

        QueryResults<Item> results = queryFactory
                .selectFrom(QItem.item)
                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(),
                                itemSearchDto.getSearchQuery()))
                .orderBy(QItem.item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Item> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression itemNmLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery) ? null : QItem.item.itemNm.like("%" + searchQuery + "%");
    }

    @Override
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        QItem item = QItem.item;
        QItemImg itemImg = QItemImg.itemImg;

        QueryResults<MainItemDto> results = queryFactory
                .select(
                        new QMainItemDto(
                                item.id,
                                item.itemNm,
                                item.itemDetail,
                                itemImg.imgUrl,
                                item.price)
                )
                .from(itemImg)
                .join(itemImg.item, item)
                .where(itemImg.repimgYn.eq("Y"))
                .where(itemNmLike(itemSearchDto.getSearchQuery()))
                .orderBy(item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MainItemDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

//    @Override
//    public Page<MainItemDto> getBestItemPage(ItemSearchDto itemSearchDto,Pageable pageable) {
//        QItem item = QItem.item;
//        QItem oitem = new QItem("oitem");
//        QItemImg itemImg = QItemImg.itemImg;
//        QOrderItem orderItem = QOrderItem.orderItem;
//        ItemSellStatus itemSellStatus = ItemSellStatus.SELL;
//
//        QueryResults<MainItemDto> results = queryFactory
//                .select(
//                        new QMainItemDto(
//                                item.id,
//                                item.itemNm,
//                                item.itemDetail,
//                                itemImg.imgUrl,
//                                item.price)
//                )
//                .from(itemImg,item)
//                .join(orderItem)
//                .where(Expressions.stringTemplate("DATE_FORMAT({0}, {1})", orderItem.regTime, ConstantImpl.create("%Y%m%d")).eq(today()))
//                .where(item.itemSellStatus.eq(itemSellStatus))
//                .where(item.id.eq(orderItem.item.id))
//                .orderBy(orderItem.id.count().desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetchResults();
//
//        List<MainItemDto> content = results.getResults();
//        long total = results.getTotal();
//        return new PageImpl<>(content, pageable, total);
//    }
//
//    private String today(){
//        SimpleDateFormat format1 = new SimpleDateFormat ( "yyyyMMdd");
//        Calendar time = Calendar.getInstance();
//        return format1.format(time.getTime());
//    }

//    public PageImpl todayItems(){
//        QItem item = QItem.item;
//        QOrderItem orderItem = QOrderItem.orderItem;
//        ItemSellStatus itemSellStatus = ItemSellStatus.SELL;
//
//        QueryResults<MainItemDto> results = queryFactory
//                .selectFrom(
//                        orderItem.item
//                )
//                .where(Expressions.stringTemplate("DATE_FORMAT({0}, {1})", orderItem.regTime, ConstantImpl.create("%y-%m")).eq(today()))
//                .fetchResults();
//
//        List<MainItemDto> content = results.getResults();
//        return new PageImpl<>(content);
//    }

}