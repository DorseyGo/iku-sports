/*
 * File: OrderServiceImpl
 * Author: DorSey Q F TANG
 * Created: 2020/4/22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service.impl;

import com.iku.sports.mini.admin.entity.Order;
import com.iku.sports.mini.admin.exception.ApiServiceException;
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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service("orderService")
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(@Qualifier("orderRepository") OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Short> getPurchasedCourseIdsByUserId(String userId) {
        List<Order> orders = orderRepository.findOrderByUserId(userId);
        if (CollectionUtils.isEmpty(orders)) {
            return Collections.emptyList();
        }

        return orders.stream()
                .filter(order -> Constants.ProductType.COURSE.getCode() == order.getProductType())
                .map(Order::getProductId)
                .map(Short::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public void deleteOrdeById(final String orderId) throws ApiServiceException {
        orderRepository.deleteById(orderId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public void updateTransIdAndPaidTimeById(String transactionId, Date paidTime, String orderId) throws
            DataAccessException {
        this.orderRepository.updateTransIdAndPaidTimeById(transactionId, paidTime, orderId);
    }

}