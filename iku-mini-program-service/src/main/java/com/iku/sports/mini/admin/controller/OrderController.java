/*
 * File: OrderController
 * Author: DorSey Q F TANG
 * Created: 2020/4/22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.controller;

import com.iku.sports.mini.admin.entity.Order;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.model.Response;
import com.iku.sports.mini.admin.request.NewOrderRequest;
import com.iku.sports.mini.admin.service.OrderService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(
            @Qualifier("orderService") final OrderService orderService) {this.orderService = orderService;}

    @ResponseBody
    @PostMapping("/api/order")
    public Response<Order> order(@RequestBody NewOrderRequest request) throws ApiServiceException {
        final Order order = orderService.saveAndReturn(request);
        return new Response<Order>().status(Response.SUCCESS).data(order);
    }
}
