/*
 * File: Order
 * Author: DorSey Q F TANG
 * Created: 2020/4/22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.entity;

import com.iku.sports.mini.admin.model.Constants;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class Order {
    private String orderId;
    private String productName;
    private int fee;
    private BigDecimal discount;
    private int moneyPaid;
    private long moneyRefund;

    /**
     * 0, for unpaid,
     * 1, for paid
     * 2, for refund
     * 3, for cancel
     */
    @Builder.Default
    private char status = Constants.OrderStatus.UN_PAID.getCode();
    private String productId;
    private short productType;
    private String userId;
    private Date paidTime;
    private Date createdTime;
    private Date lastModifyTime;

    // ---------------------------
    // transaction id, generated when this order is paid
    // ---------------------------
    private String transactionId;

    @Tolerate
    public Order() {
        // empty
    }

}
