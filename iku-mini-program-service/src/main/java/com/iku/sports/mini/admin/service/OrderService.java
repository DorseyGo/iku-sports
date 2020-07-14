/*
 * File: OrderService
 * Author: DorSey Q F TANG
 * Created: 2020/4/22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service;

import com.iku.sports.mini.admin.entity.CourseOrder;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.model.Paging;

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
     * @param paidTime      the paid time.
     * @param orderId       the order ID.
     */
    void updateTransIdAndPaidTimeById(String transactionId, Date paidTime, String orderId);

    /**
     * return the sort of unique id of purchased course specified by {@code userId}
     *
     * @param userId unique id of user
     * @return
     */
    List<Short> getPurchasedCourseIdsByUserId(String userId);

    /**
     * Delete the order from underlying database according to the given order ID.
     *
     * @param orderId the order id.
     * @throws ApiServiceException if any errors detected during process.
     */
    void deleteOrderById(String orderId) throws ApiServiceException;

    /**
     * Paginate the orders according to the given conditions.
     *
     * @param curPage the current page.
     * @param userId the user ID.
     * @param status the status.
     * @return the page.
     */
    Paging<CourseOrder> paginateByUserIdAndStatus(int curPage, String userId, char status);
}
