package com.shop.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
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

public class BidRepositoryImpl implements BidRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public BidRepositoryImpl(EntityManager em) {
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

        QueryResults<BidDto> results = queryFactory
                .select(
                        new QBidDto(
                                itemImg.imgUrl,
                                item.itemNm,
                                member.email,
                                bid.depositType,
                                bid.depositName,
                                bid.depositAmount,
                                bid.approvedYn,
                                bid.approvedTime,
                                bid.regTime
                        )
                )
                .from(bid)
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
    public Page<BidDto> getUserBidPage(BidSearchDto bidSearchDto, Pageable pageable) {
        return null;
    }

}