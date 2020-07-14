/*
 * File: OrderController
 * Author: DorSey Q F TANG
 * Created: 2020/4/22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.controller;

import com.iku.sports.mini.admin.entity.CourseOrder;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.model.Paging;
import com.iku.sports.mini.admin.model.Response;
import com.iku.sports.mini.admin.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public Response<String> deleteOrderById(@PathVariable("orderId") @NotNull final String orderId) throws
            ApiServiceException {
        orderService.deleteOrderById(orderId);
        return Response.ok();
    }

    @GetMapping("/orders/{curPage}")
    public Response<Paging<CourseOrder>> paginate(@PathVariable("curPage") final int curPage,
            @RequestParam("userId") final String userId,
            @RequestParam("status") final char status) throws ApiServiceException {
        Paging<CourseOrder> paging = orderService.paginateByUserIdAndStatus(curPage, userId, status);
        return Response.ok(paging);
    }
}
