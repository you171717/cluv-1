package com.shop.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Constant;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.constant.ReverseAuctionSearchSortColumn;
import com.shop.dto.*;
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
                                reverseAuction.id,
                                itemImg.imgUrl,
                                item.itemNm,
                                item.price,
                                reverseAuction.startTime,
                                reverseAuction.priceUnit,
                                reverseAuction.timeUnit,
                                reverseAuction.maxRate,
                                new CaseBuilder()
                                        .when(outOfDate().not())
                                        .then("F")
                                        .otherwise(
                                            new Coalesce<String>(String.class).add(
                                                JPAExpressions.select(bid.approvedYn)
                                                        .from(bid)
                                                        .where(bid.reverseAuction.eq(reverseAuction))
                                                        .where(bid.approvedYn.eq("Y"))
                                            ).add("N")
                                        )
                        )
                )
                .from(reverseAuction)
                .join(reverseAuction.item, item)
                .join(itemImg).on(itemImg.item.eq(reverseAuction.item).and(itemImg.repImgYn.eq("Y")))
                .where(searchByLike(reverseAuctionSearchDto.getSearchQuery()))
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
                                reverseAuction.id,
                                itemImg.imgUrl,
                                item.itemNm,
                                item.price,
                                reverseAuction.startTime,
                                reverseAuction.priceUnit,
                                reverseAuction.timeUnit,
                                reverseAuction.maxRate,
                                Expressions.constant("N")
                        )
                )
                .from(reverseAuction)
                .join(reverseAuction.item, item)
                .join(itemImg).on(itemImg.item.eq(reverseAuction.item).and(itemImg.repImgYn.eq("Y")))
                .where(searchByLike(reverseAuctionSearchDto.getSearchQuery()))
                .where(outOfDate())
                .where(
                    JPAExpressions.select(bid.approvedYn)
                            .from(bid)
                            .where(bid.reverseAuction.eq(reverseAuction))
                            .where(bid.approvedYn.eq("Y"))
                            .isNull()
                )
                .orderBy(this.orderBy(reverseAuctionSearchDto))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ReverseAuctionDto> content = results.getResults();

        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public List<ReverseAuctionHistoryDto> getPreviousReverseAuctionPage() {
        QReverseAuction reverseAuction = QReverseAuction.reverseAuction;
        QItemImg itemImg = QItemImg.itemImg;
        QItem item = QItem.item;
        QBid bid = QBid.bid;
        QMember member = QMember.member;

        QueryResults<ReverseAuctionHistoryDto> results = queryFactory
                .select(
                        new QReverseAuctionHistoryDto(
                                reverseAuction.id,
                                itemImg.imgUrl,
                                item.itemNm,
                                item.price,
                                reverseAuction.startTime,
                                reverseAuction.priceUnit,
                                reverseAuction.timeUnit,
                                reverseAuction.maxRate,
                                bid.approvedYn,
                                bid.approvedTime,
                                member.email,
                                new CaseBuilder()
                                        .when(bid.approvedTime.loe(LocalDateTime.now().minusHours(24)))
                                        .then(Expressions.constant(-1))
                                        .otherwise(bid.depositAmount)
                        )
                )
                .from(reverseAuction)
                .join(reverseAuction.bids, bid).on(bid.approvedYn.eq("Y"))
                .join(bid.member, member)
                .join(reverseAuction.item, item)
                .join(itemImg).on(itemImg.item.eq(reverseAuction.item).and(itemImg.repImgYn.eq("Y")))
                .where(outOfDate().not().or(bid.approvedYn.isNotNull()))
                .orderBy(bid.approvedTime.desc())
                .limit(6)
                .fetchResults();

        return results.getResults();
    }

    @Override
    public ReverseAuctionDto getUserReverseAuctionDetailPage(Long id) {
        QReverseAuction reverseAuction = QReverseAuction.reverseAuction;
        QItemImg itemImg = QItemImg.itemImg;
        QItem item = QItem.item;

        ReverseAuctionDto result = queryFactory
                .select(
                        new QReverseAuctionDto(
                                reverseAuction.id,
                                itemImg.imgUrl,
                                item.itemNm,
                                item.price,
                                reverseAuction.startTime,
                                reverseAuction.priceUnit,
                                reverseAuction.timeUnit,
                                reverseAuction.maxRate,
                                Expressions.constant("N")
                        )
                )
                .from(reverseAuction)
                .join(reverseAuction.item, item)
                .join(itemImg).on(itemImg.item.eq(reverseAuction.item).and(itemImg.repImgYn.eq("Y")))
                .where(reverseAuction.id.eq(id))
                .fetchOne();

        return result;
    }

}
