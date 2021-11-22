package com.shop.constant;

import org.junit.jupiter.api.Test;

class ReverseAuctionSearchSortColumnTest {

    @Test
    public void enumTest() {
        System.out.println(ReverseAuctionSearchSortColumn.valueOf("REG_TIME"));
        System.out.println(ReverseAuctionSearchSortColumn.REG_TIME.toString());
    }

}