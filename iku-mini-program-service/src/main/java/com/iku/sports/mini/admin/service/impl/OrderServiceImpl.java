/*
 * File: OrderServiceImpl
 * Author: DorSey Q F TANG
 * Created: 2020/4/22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service.impl;

import com.iku.sports.mini.admin.config.IkuSportsConfig;
import com.iku.sports.mini.admin.entity.Order;
import com.iku.sports.mini.admin.entity.User;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.repository.OrderRepository;
import com.iku.sports.mini.admin.request.NewOrderRequest;
import com.iku.sports.mini.admin.service.OrderService;
import com.iku.sports.mini.admin.service.UserService;
import com.iku.sports.mini.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
                    .map(Order::getCourseId)
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());
    }
}