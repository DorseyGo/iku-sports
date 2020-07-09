/*
 * File: OrderController
 * Author: DorSey Q F TANG
 * Created: 2020/4/22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.controller;

import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.model.Response;
import com.iku.sports.mini.admin.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@Validated
@RestController
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(
            @Qualifier("orderService") final OrderService orderService) {
        this.orderService = orderService;
    }

    @DeleteMapping("/orders/{orderId}")
    public Response<String> deleteOrderById(@PathVariable("orderId") @NotNull final String orderId) throws ApiServiceException {
        orderService.deleteOrdeById(orderId);
        return Response.ok();
    }
}
