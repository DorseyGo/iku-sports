/*
 * File: PaymentController
 * Author: DorSey Q F TANG
 * Created: 2020/4/22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.controller;

import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.model.Prepayment;
import com.iku.sports.mini.admin.model.Response;
import com.iku.sports.mini.admin.request.CoursePrepaymentRequest;
import com.iku.sports.mini.admin.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller, which is used to handle the request for payment.
 */
@Slf4j
@RestController
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(
            @Qualifier("paymentService") PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Uniform order the course, and fetch the prepay ID which returned from WeChat server.
     *
     * @return
     */
    @PostMapping("/payment/course")
    public Response<Prepayment> prePayCourse(
            @RequestBody final CoursePrepaymentRequest request) throws ApiServiceException {
        final Prepayment prepayment = paymentService.prepayCourse(request.getUserId(), request.getCourseId());
        return Response.ok(prepayment);
    }
}
