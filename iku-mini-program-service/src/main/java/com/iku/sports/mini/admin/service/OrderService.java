/*
 * File: OrderService
 * Author: DorSey Q F TANG
 * Created: 2020/4/22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service;

import java.util.List;

/**
 * The business service, which focuses on process <tt>order</tt> resource.
 */
public interface OrderService {

    void updateTransIdAndPaidTimeById(String transactionId, String endTime, String orderId);
    /**
     * return the sort of unique id of purchased course specified by {@code userId}
     * @param userId unique id of user
     * @return
     */
    List<Integer> findPurchasedCourse(String userId);
}
