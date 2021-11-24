package com.shop.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.constant.BidSearchSortColumn;
import com.shop.dto.BidDto;
import com.shop.dto.BidSearchDto;
import com.shop.dto.QBidDto;
import com.shop.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

public class BidRepositoryCustomImpl implements BidRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public BidRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchByLike(String searchQuery) {
        return QBid.bid.reverseAuction.item.itemNm.like("%" + searchQuery + "%");
    }

    private OrderSpecifier orderBy(BidSearchDto bidSearchDto) {
        BidSearchSortColumn sortColumn = bidSearchDto.getSortColumn();

        OrderSpecifier orderSpecifier = null;

        if(sortColumn.equals(BidSearchSortColumn.REG_TIME)) {
            orderSpecifier = QBid.bid.regTime.desc();
        } else if(sortColumn.equals(BidSearchSortColumn.APPROVED_YN)) {
            orderSpecifier = QBid.bid.approvedYn.asc();
        }

        return orderSpecifier;
    }

    @Override
    public Page<BidDto> getAdminBidPage(BidSearchDto bidSearchDto, Pageable pageable) {
        QReverseAuction reverseAuction = QReverseAuction.reverseAuction;
        QItemImg itemImg = QItemImg.itemImg;
        QMember member = QMember.member;
        QItem item = QItem.item;
        QBid bid = QBid.bid;
        QBid bid2 = new QBid("bid2");

        QueryResults<BidDto> results = queryFactory
                .select(
                        new QBidDto(
                                bid.id,
                                itemImg.imgUrl,
                                item.itemNm,
                                member.email,
                                bid.depositType,
                                bid.depositName,
                                bid.depositAmount,
                                new CaseBuilder()
                                        .when(bid2.approvedYn.isNull())
                                        .then(bid.approvedYn)
                                        .otherwise("F"),
                                bid.approvedTime,
                                bid.regTime
                        )
                )
                .from(bid)
                .leftJoin(bid2).on(
                        bid2.reverseAuction
                                .eq(bid.reverseAuction)
                                .and(bid2.approvedYn.eq("Y"))
                                .and(bid2.id.ne(bid.id))
                )
                .join(bid.member, member)
                .join(bid.reverseAuction, reverseAuction)
                .join(bid.reverseAuction.item, item)
                .join(itemImg).on(itemImg.item.eq(bid.reverseAuction.item).and(itemImg.repImgYn.eq("Y")))
                .where(searchByLike(bidSearchDto.getSearchQuery()))
                .orderBy(this.orderBy(bidSearchDto))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<BidDto> content = results.getResults();

        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<BidDto> getUserBidPage(Member curMember, BidSearchDto bidSearchDto, Pageable pageable) {
        QReverseAuction reverseAuction = QReverseAuction.reverseAuction;
        QItemImg itemImg = QItemImg.itemImg;
        QMember member = QMember.member;
        QItem item = QItem.item;
        QBid bid = QBid.bid;
        QBid bid2 = new QBid("bid2");

        QueryResults<BidDto> results = queryFactory
                .select(
                        new QBidDto(
                                bid.id,
                                itemImg.imgUrl,
                                item.itemNm,
                                member.email,
                                bid.depositType,
                                bid.depositName,
                                bid.depositAmount,
                                new CaseBuilder()
                                        .when(bid2.approvedYn.isNull())
                                        .then(bid.approvedYn)
                                        .otherwise("F"),
                                bid.approvedTime,
                                bid.regTime
                        )
                )
                .from(bid)
                .leftJoin(bid2).on(
                        bid2.reverseAuction
                                .eq(bid.reverseAuction)
                                .and(bid2.approvedYn.eq("Y"))
                                .and(bid2.id.ne(bid.id))
                )
                .join(bid.member, member)
                .join(bid.reverseAuction, reverseAuction)
                .join(bid.reverseAuction.item, item)
                .join(itemImg).on(itemImg.item.eq(bid.reverseAuction.item).and(itemImg.repImgYn.eq("Y")))
                .where(searchByLike(bidSearchDto.getSearchQuery()))
                .where(bid.member.eq(curMember))
                .orderBy(this.orderBy(bidSearchDto))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<BidDto> content = results.getResults();

        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

}