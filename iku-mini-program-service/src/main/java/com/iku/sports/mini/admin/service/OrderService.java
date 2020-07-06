/*
 * File: OrderService
 * Author: DorSey Q F TANG
 * Created: 2020/4/22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service;

import com.iku.sports.mini.admin.entity.Order;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.request.NewOrderRequest;

import javax.validation.constraints.NotNull;

/**
 * The business service, which focuses on process <tt>order</tt> resource.
 */
public interface OrderService {

    void updateTransIdAndPaidTimeById(String transactionId, String endTime, String orderId);
}
