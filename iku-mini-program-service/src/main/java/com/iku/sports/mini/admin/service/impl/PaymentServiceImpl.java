/*
 * File: PaymentServiceImpl
 * Author: DorSey Q F TANG
 * Created: 2020/4/22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service.impl;

import com.iku.sports.mini.admin.config.IkuSportsConfig;
import com.iku.sports.mini.admin.entity.Course;
import com.iku.sports.mini.admin.entity.Order;
import com.iku.sports.mini.admin.entity.User;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.exception.IkuSportsError;
import com.iku.sports.mini.admin.generator.OrderGenerator;
import com.iku.sports.mini.admin.model.Constants;
import com.iku.sports.mini.admin.model.Prepayment;
import com.iku.sports.mini.admin.repository.OrderRepository;
import com.iku.sports.mini.admin.response.PrepaymentResponse;
import com.iku.sports.mini.admin.service.CourseService;
import com.iku.sports.mini.admin.service.PaymentService;
import com.iku.sports.mini.admin.service.UserService;
import com.iku.sports.mini.admin.service.WxApiService;
import com.iku.sports.mini.admin.utils.Obj2Map;
import com.iku.sports.mini.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.util.crypto.WxCryptUtil;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.RoundingMode;

@Slf4j
@Transactional
@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {

    private final UserService userService;
    private final CourseService courseService;
    private final OrderRepository orderRepository;
    private final WxApiService wxApiService;
    private final IkuSportsConfig config;

    @Autowired
    public PaymentServiceImpl(@Qualifier("userService") final UserService userService,
            @Qualifier("courseService") final CourseService courseService,
            @Qualifier("orderRepository") final OrderRepository orderRepository,
            @Qualifier("wxApiService") final WxApiService wxApiService,
            final IkuSportsConfig config) {
        this.userService = userService;
        this.courseService = courseService;
        this.orderRepository = orderRepository;
        this.wxApiService = wxApiService;
        this.config = config;
    }

    @Override
    public Prepayment prepayCourse(String userId, short courseId) throws ApiServiceException, DataAccessException {
        return prepay(userId, courseId, (usrId, cId) -> createOrderAndReturn(usrId, cId));
    }

    private Order createOrderAndReturn(String userId, Short courseId) throws ApiServiceException {
        final Course course = courseService.getCourseById(courseId);
        if (course == null) {
            log.error("==> No course found by id: {}", courseId);
            throw new ApiServiceException(IkuSportsError.INTERNAL_ERR);
        }

        final Order order = Order.builder().orderId(Utils.genUniqueStr())
                .fee((course.getFee() == null) ? 0 : course.getFee().multiply(Constants.ONE_HUNDRED)
                .setScale(0, RoundingMode.HALF_UP).intValue())
                .productId(String.valueOf(courseId))
                .productType(Constants.ProductType.COURSE.getCode())
                .productName(course.getName())
                .userId(userId).build();
        orderRepository.save(order);
        return order;
    }

    private String getOpenId(final String userId) throws ApiServiceException {
        final User user = userService.getUserById(userId);
        if (Strings.isNullOrEmpty(user.getOpenId())) {
            log.error("==> the open id for the given id: {} is null or empty", userId);
            throw new ApiServiceException(IkuSportsError.INTERNAL_ERR);
        }

        return user.getOpenId();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public <T> Prepayment prepay(final String userId, final T productId, final OrderGenerator<T> generator) throws
            ApiServiceException,
            DataAccessException {
        if (userId == null || Strings.isNullOrEmpty(userId)) {
            log.error("==> Passed in user id is null or empty");
            throw new ApiServiceException(IkuSportsError.INTERNAL_ERR);
        }

        // perform uniform order on WeChat and get the prepay ID.
        final String openId = this.getOpenId(userId);
        final Order order = generator.genOrder(userId, productId);
        final PrepaymentResponse resp = wxApiService
                .getPrepayId(openId, order.getOrderId(), order.getFee(), order.getProductName(),
                        Constants.TradeType.JSAPI, config.getMachineIpAddr(), config.getNotifyUrlAddr());

        // construct the prepayment for later payment.
        final Prepayment prepayment = Prepayment.builder().appId(resp.getAppId())
                .nonce(resp.getNonce()).timeStamp(Utils.getTimestamp())
                .pckage(Utils.genWxPackage(resp.getPrepayId())).build();
        final String sign = WxCryptUtil.createSign(Obj2Map.fromObj(prepayment), config.getWxApiKey());
        prepayment.setSign(sign);

        return prepayment;
    }

}
