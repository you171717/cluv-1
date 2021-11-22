package com.shop.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.constant.ReverseAuctionSearchSortColumn;
import com.shop.dto.QReverseAuctionDto;
import com.shop.dto.ReverseAuctionDto;
import com.shop.dto.ReverseAuctionSearchDto;
import com.shop.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class ReverseAuctionRepositoryCustomImpl implements ReverseAuctionRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public ReverseAuctionRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchByLike(String searchQuery) {
        return QReverseAuction.reverseAuction.item.itemNm.like("%" + searchQuery + "%");
    }

    private OrderSpecifier orderBy(ReverseAuctionSearchDto reverseAuctionSearchDto) {
        ReverseAuctionSearchSortColumn sortColumn = reverseAuctionSearchDto.getSortColumn();
        Sort.Direction sortDirection = reverseAuctionSearchDto.getSortDirection();

        OrderSpecifier orderSpecifier = null;

        if(sortColumn.equals(ReverseAuctionSearchSortColumn.REG_TIME)) {
             orderSpecifier = QReverseAuction.reverseAuction.regTime.desc();
        } else if(sortColumn.equals(ReverseAuctionSearchSortColumn.PRICE)) {
            if(sortDirection.isAscending()) {
                orderSpecifier = QReverseAuction.reverseAuction.item.price.asc();
            } else {
                orderSpecifier = QReverseAuction.reverseAuction.item.price.desc();
            }
        }

        return orderSpecifier;
    }

    private BooleanExpression outOfDate() {
        return Expressions.stringTemplate("TIMESTAMPDIFF(HOUR, {0}, {1})", QReverseAuction.reverseAuction.startTime, LocalDateTime.now())
                .castToNum(Integer.class)
                .multiply(QReverseAuction.reverseAuction.priceUnit)
                .divide(QReverseAuction.reverseAuction.item.price)
                .multiply(100)
                .loe(QReverseAuction.reverseAuction.maxRate);
    }

    @Override
    public Page<ReverseAuctionDto> getAdminReverseAuctionPage(ReverseAuctionSearchDto reverseAuctionSearchDto, Pageable pageable) {
        QReverseAuction reverseAuction = QReverseAuction.reverseAuction;
        QItemImg itemImg = QItemImg.itemImg;
        QItem item = QItem.item;
        QBid bid = QBid.bid;


        QueryResults<ReverseAuctionDto> results = queryFactory
                .select(
                        new QReverseAuctionDto(
                                itemImg.imgUrl,
                                item.itemNm,
                                item.price,
                                reverseAuction.startTime,
                                reverseAuction.priceUnit,
                                reverseAuction.timeUnit,
                                reverseAuction.maxRate,
                                bid.approvedYn.coalesce("N")
                        )
                )
                .from(reverseAuction)
                .leftJoin(reverseAuction.bids, bid)
                .join(reverseAuction.item, item)
                .join(itemImg).on(itemImg.item.eq(reverseAuction.item).and(itemImg.repImgYn.eq("Y")))
                .where(searchByLike(reverseAuctionSearchDto.getSearchQuery()))
                .where(outOfDate())
                .orderBy(this.orderBy(reverseAuctionSearchDto))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ReverseAuctionDto> content = results.getResults();

        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<ReverseAuctionDto> getUserReverseAuctionPage(ReverseAuctionSearchDto reverseAuctionSearchDto, Pageable pageable) {
        QReverseAuction reverseAuction = QReverseAuction.reverseAuction;
        QItemImg itemImg = QItemImg.itemImg;
        QItem item = QItem.item;
        QBid bid = QBid.bid;

        QueryResults<ReverseAuctionDto> results = queryFactory
                .select(
                        new QReverseAuctionDto(
                                itemImg.imgUrl,
                                item.itemNm,
                                item.price,
                                reverseAuction.startTime,
                                reverseAuction.priceUnit,
                                reverseAuction.timeUnit,
                                reverseAuction.maxRate,
                                bid.approvedYn.coalesce("N")
                        )
                )
                .where(searchByLike(reverseAuctionSearchDto.getSearchQuery()))
                .orderBy(this.orderBy(reverseAuctionSearchDto))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ReverseAuctionDto> content = results.getResults();

        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

}
