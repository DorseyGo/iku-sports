/*
 * File: PaymentController
 * Author: DorSey Q F TANG
 * Created: 2020/4/22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.controller;

import com.iku.sports.mini.admin.config.IkuSportsConfig;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.model.*;
import com.iku.sports.mini.admin.request.PaymentRequest;
import com.iku.sports.mini.admin.response.PrepaymentResponse;
import com.iku.sports.mini.admin.service.PaymentService;
import com.iku.sports.mini.admin.utils.Utils;
import com.iku.sports.mini.admin.utils.XMLUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
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
