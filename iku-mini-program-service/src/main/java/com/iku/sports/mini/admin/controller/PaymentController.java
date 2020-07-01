/*
 * File: PaymentController
 * Author: DorSey Q F TANG
 * Created: 2020/4/22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.controller;

import com.iku.sports.mini.admin.config.IkuSportsConfig;
import com.iku.sports.mini.admin.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PaymentController {

    private final PaymentService paymentService;
    private final IkuSportsConfig config;

    @Autowired
    public PaymentController(
            @Qualifier("paymentService") PaymentService paymentService,
            IkuSportsConfig config) {
        this.paymentService = paymentService;
        this.config = config;
    }
}
