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

    @ResponseBody
    @PostMapping("/api/payment")
    public Response<PrepaymentResponse> prepay(@RequestBody PaymentRequest request) throws ApiServiceException {
        String prepayId = paymentService.pay(request);
        Prepayment prepayment = Prepayment.builder()
                .appId(config.getAppId())
                .timeStamp(Utils.getTimestamp())
                .nonceStr(Utils.genUUID(32))
                .pckage(Constants.PREFIX_PREPAY_ID + prepayId)
                .build();

        String sign = Utils.sign(Utils.toSortedMap(prepayment), config.getKey());
        PrepaymentResponse response = (PrepaymentResponse) prepayment;
        response.setSign(sign);

        return new Response<PrepaymentResponse>().status(Response.SUCCESS).data(response);
    }

    @GetMapping("/api/payment/notification")
    public void notify(final HttpServletRequest request, final HttpServletResponse response) throws
            ApiServiceException, IOException {
        byte[] bytesRead = Utils.read(request.getInputStream());
        PaymentNotification notification = XMLUtils
                .convertXmlStrToObj(PaymentNotification.class, new String(bytesRead, StandardCharsets.UTF_8));

        log.info("Weixin callback arguments: {}", notification);
        String resXml = null;
        if (Constants.SUCCESS.equalsIgnoreCase(notification.getReturnCode())
            && Constants.SUCCESS.equalsIgnoreCase(notification.getResultCode())) {
            // FIXME give an update on order
            resXml = Result.builder().returnCode(Constants.SUCCESS).returnMsg(Constants.OK).build().toXml();
        } else {
            resXml = Result.builder().returnCode(Constants.FAIL).returnMsg(notification.getErrCodeDesc()).build()
                    .toXml();
        }

        log.info("Response to Weixin: {}", resXml);
        writeToResponse(resXml, response);
    }

    private void writeToResponse(String resXml, HttpServletResponse response) throws IOException {
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(resXml);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
