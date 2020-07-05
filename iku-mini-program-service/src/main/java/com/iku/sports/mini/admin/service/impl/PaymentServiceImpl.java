/*
 * File: PaymentServiceImpl
 * Author: DorSey Q F TANG
 * Created: 2020/4/22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service.impl;

import com.iku.sports.mini.admin.entity.User;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.exception.IkuSportsError;
import com.iku.sports.mini.admin.model.Prepayment;
import com.iku.sports.mini.admin.repository.OrderRepository;
import com.iku.sports.mini.admin.service.PaymentService;
import com.iku.sports.mini.admin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {

    private final UserService userService;
    private final OrderRepository orderRepository;

    @Autowired
    public PaymentServiceImpl(@Qualifier("userService") final UserService userService,
            @Qualifier("orderRepository") OrderRepository orderRepository) {
        this.userService = userService;
        this.orderRepository = orderRepository;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public Prepayment prepayCourse(String userId, short courseId) throws ApiServiceException, DataAccessException {
        if (userId == null || Strings.isNullOrEmpty(userId)) {
            log.error("==> Passed in user id is null or empty");
            throw new ApiServiceException(IkuSportsError.INTERNAL_ERR);
        }

        final String openId = this.getOpenId(userId);


        return null;
    }

    private String getOpenId(final String userId) throws ApiServiceException {
        final User user = userService.getUserById(userId);
        if (Strings.isNullOrEmpty(user.getOpenId())) {
            log.error("==> the open id for the given id: {} is null or empty", userId);
            throw new ApiServiceException(IkuSportsError.INTERNAL_ERR);
        }

        return user.getOpenId();
    }
}
