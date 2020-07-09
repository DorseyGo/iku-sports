/*
 * File: OrderService
 * Author: DorSey Q F TANG
 * Created: 2020/4/22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service;

import java.util.Date;
import java.util.List;

/**
 * The business service, which focuses on process <tt>order</tt> resource.
 */
public interface OrderService {

    /**
     * Update the transaction ID and paid time according to the order ID.
     *
     * @param transactionId the transaction ID.
     * @param paidTime the paid time.
     * @param orderId the order ID.
     */
    void updateTransIdAndPaidTimeById(String transactionId, Date paidTime, String orderId);

    /**
     * return the sort of unique id of purchased course specified by {@code userId}
     * @param userId unique id of user
     * @return
     */
    List<Short> getPurchasedCourseIdsByUserId(String userId);
}
