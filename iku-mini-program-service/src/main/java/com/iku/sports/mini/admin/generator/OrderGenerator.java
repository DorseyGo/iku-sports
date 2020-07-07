/*
 * File: OrderGenerator
 * Author: DorSey Q F TANG
 * Created: 2020/7/6
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.generator;

import com.iku.sports.mini.admin.entity.Order;
import com.iku.sports.mini.admin.exception.ApiServiceException;

@FunctionalInterface
public interface OrderGenerator<T> {
    Order genOrder(final String userId, final T productId) throws ApiServiceException;
}
