/*
 * File: OrderServiceImpl
 * Author: DorSey Q F TANG
 * Created: 2020/4/22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service.impl;

import com.iku.sports.mini.admin.constant.OrderTypeEnum;
import com.iku.sports.mini.admin.entity.Order;
import com.iku.sports.mini.admin.model.Constants;
import com.iku.sports.mini.admin.repository.OrderRepository;
import com.iku.sports.mini.admin.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.Date;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service("orderService")
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Integer> findPurchasedCourse(String userId) {
        List<Order> orders = orderRepository.findOrderByUserId(userId);
        if (CollectionUtils.isEmpty(orders)) {
            return Collections.emptyList();
        }

        return orders.stream()
                    .filter(order -> OrderTypeEnum.COURSE.getCode() == order.getProductType())
                    .map(Order::getProductId)
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public void updateTransIdAndPaidTimeById(String transactionId, String endTime, String orderId) throws
            DataAccessException {
        Date paidTime = null;
        try {
            paidTime = Constants.DATE_FORMATTER_WECHAT.get().parse(endTime);
        } catch (ParseException e) {
            log.error("==> Failed to parse the time: {}", endTime, e);
            paidTime = new Date();
        }

        this.orderRepository.updateTransIdAndPaidTimeById(transactionId, paidTime, orderId);
    }
}