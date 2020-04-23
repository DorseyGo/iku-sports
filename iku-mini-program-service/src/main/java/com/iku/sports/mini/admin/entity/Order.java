/*
 * File: Order
 * Author: DorSey Q F TANG
 * Created: 2020/4/22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class Order {
    private String orderId;
    private int fee;
    private short courseId;
    private String userId;
    private float discount;
    private int moneyPaid;
    private long refundMoney;

    /**
     * 0, for unpaid,
     * 1, for paid
     * 2, for refund
     * 3, for cancel
     */
    @Builder.Default
    private char status = OrderStatus.UN_PAID.getCode();
    // computed by course id.
    private String productName;

    @Tolerate
    public Order() {
        // empty
    }

    /**
     * Enumerate all available order status.
     */
    public enum OrderStatus {
        UN_PAID('0'), PAID('1'), REFUND('2'), CANCEL('3');
        private char code;

        OrderStatus(final char code) {
            this.code = code;
        }

        public static OrderStatus orderStatus(final char code) {
            final OrderStatus[] orderStatuses = OrderStatus.values();
            for (int index = 0; index < orderStatuses.length; index++) {
                if (orderStatuses[index].code == code) {
                    return orderStatuses[index];
                }
            }

            throw new RuntimeException("code " + code + " is not recognized as order status");
        }

        public char getCode() {
            return code;
        }
    }
}
