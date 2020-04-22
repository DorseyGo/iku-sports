/*
 * File: PaymentServiceImpl
 * Author: DorSey Q F TANG
 * Created: 2020/4/22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service.impl;

import com.google.common.base.Strings;
import com.iku.sports.mini.admin.config.IkuSportsConfig;
import com.iku.sports.mini.admin.entity.Order;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.exception.IkuSportsError;
import com.iku.sports.mini.admin.model.Constants;
import com.iku.sports.mini.admin.request.GetPrepayIdRequest;
import com.iku.sports.mini.admin.request.PaymentRequest;
import com.iku.sports.mini.admin.service.OrderService;
import com.iku.sports.mini.admin.service.PaymentService;
import com.iku.sports.mini.admin.service.UserService;
import com.iku.sports.mini.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpPrepayIdResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {

    private final OrderService orderService;
    private final UserService userService;
    private final IkuSportsConfig config;
    private final WxMpService wxMpService;

    @Autowired
    public PaymentServiceImpl(@Qualifier("orderService") final OrderService orderService,
            @Qualifier("userService") final UserService userService,
            IkuSportsConfig config, WxMpService wxMpService) {
        this.orderService = orderService;
        this.userService = userService;
        this.config = config;
        this.wxMpService = wxMpService;
    }

    @Override
    public String pay(final PaymentRequest request) throws ApiServiceException {
        GetPrepayIdRequest payment = validateAndCreatePaymentReq(request);
        WxMpPrepayIdResult result = wxMpService.getPrepayId(Utils.toMap(payment));

        if (Constants.SUCCESS.equalsIgnoreCase(result.getReturn_code())
            && Constants.SUCCESS.equalsIgnoreCase(result.getResult_code())) {
            return result.getPrepay_id();
        }

        log.error("Failed to prepay the order: {}, due to error: {}", request.getOrderNo(), result.getErr_code_des());
        throw new ApiServiceException(IkuSportsError.REQ_WX_API_ERROR);
    }

    private GetPrepayIdRequest validateAndCreatePaymentReq(final PaymentRequest request) throws ApiServiceException {
        if (Strings.isNullOrEmpty(request.getOrderNo()) || Strings.isNullOrEmpty(request.getToken())) {
            throw new ApiServiceException(IkuSportsError.SYS_PARAMS_MISSED);
        }

        final String openId = userService.getOpenIdByToken(request.getToken());
        if (Strings.isNullOrEmpty(openId)) {
            log.error("No open ID found for token: {}", request.getToken());
            throw new ApiServiceException(IkuSportsError.INTERNAL_ERROR);
        }

        final Order order = orderService.getOrderById(request.getOrderNo());
        if (order == null) {
            log.error("No order found for order number: {}", request.getOrderNo());
            throw new ApiServiceException(IkuSportsError.ORDER_NOT_FOUND_ERROR);
        }

        if (order.getMoneyPaid() <= 0) {
            log.error("Order error, money should be paid is: {}", order.getMoneyPaid());
            throw new ApiServiceException(IkuSportsError.ORDER_ERROR);
        }

        log.info("==> try to pay the order by weixin payment: {}", order.getOrderId());
        GetPrepayIdRequest payment = GetPrepayIdRequest.builder()
                .orderNo(order.getOrderId())
                .productDescription(order.getProductName())
                .totalFee(order.getMoneyPaid())
                .terminalIpAddr(config.getMachineIpAddr())
                .notifyUrlAddr(config.getNotifyUrlAddr())
                .openId(openId).build();

        return payment;
    }
}
