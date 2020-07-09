/*
 * File: PaymentController
 * Author: DorSey Q F TANG
 * Created: 2020/4/22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.controller;

import com.google.common.base.Strings;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.model.Constants;
import com.iku.sports.mini.admin.model.PaymentNotification;
import com.iku.sports.mini.admin.model.Prepayment;
import com.iku.sports.mini.admin.model.Response;
import com.iku.sports.mini.admin.request.CoursePrepaymentRequest;
import com.iku.sports.mini.admin.response.Resp2Wechat;
import com.iku.sports.mini.admin.service.OrderService;
import com.iku.sports.mini.admin.service.PaymentService;
import com.iku.sports.mini.admin.utils.Utils;
import com.iku.sports.mini.admin.utils.XMLUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Date;

/**
 * The controller, which is used to handle the request for payment.
 */
@Slf4j
@Controller
public class PaymentController {

    private final PaymentService paymentService;
    private final OrderService orderService;

    @Autowired
    public PaymentController(
            @Qualifier("paymentService") final PaymentService paymentService,
            @Qualifier("orderService") final OrderService orderService) {
        this.paymentService = paymentService;
        this.orderService = orderService;
    }

    /**
     * Uniform order the course, and fetch the prepay ID which returned from WeChat server.
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/payment/course")
    public Response<Prepayment> prePayCourse(
            @RequestBody final CoursePrepaymentRequest request) throws ApiServiceException {
        final Prepayment prepayment = paymentService.prepayCourse(request.getUserId(), request.getCourseId());
        return Response.ok(prepayment);
    }

    /**
     * The notification received from WeChat server.
     *
     * @param request the request.
     * @return the response.
     */
    @RequestMapping(name = "/payment/notification")
    public void notify(final HttpServletRequest request, final HttpServletResponse response) throws
            IOException, ApiServiceException {
        final String xmlResp = IOUtils.toString(request.getInputStream());
        if (Strings.isNullOrEmpty(xmlResp)) {
            final String message = "==> No input reads from WeChat";
            log.error(message);
            throw new IOException(message);
        }

        Resp2Wechat resp = Resp2Wechat.builder().returnCode(Constants.FAIL).build();
        final PaymentNotification notification = XMLUtils.convertXmlStrToObj(PaymentNotification.class, xmlResp);
        if (Constants.SUCCESS.equals(notification.getReturnCode())) {
            final String orderNo = notification.getOrderNo();
            final String transactionId = notification.getTransactionId();
            final String endTime = notification.getEndTime();
            Date paidTime = null;
            try {
                paidTime = (endTime == null) ? new Date() : Constants.DATE_FORMATTER_WECHAT.get().parse(endTime);
            } catch (ParseException e) {
                log.error("==> Failed to parse the time: {}", endTime, e);
                paidTime = new Date();
            }

            orderService.updateTransIdAndPaidTimeById(transactionId, paidTime, orderNo);
            resp = Resp2Wechat.builder().returnCode(Constants.SUCCESS)
                    .returnMessage(Constants.OK).build();
        }

        writeXml(Utils.toWxXmlResponse(resp), response);
    }

    private void writeXml(final String xml, final HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_XML_VALUE + ";charset=utf8");
        final PrintWriter writer = response.getWriter();
        try {
            writer.write(xml);
            writer.flush();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

}
