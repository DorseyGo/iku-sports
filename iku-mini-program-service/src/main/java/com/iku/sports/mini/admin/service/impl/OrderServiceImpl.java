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
import com.iku.sports.mini.admin.exception.IkuSportsError;
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

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Slf4j
@Transactional
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final IkuSportsConfig config;
    private final UserService userService;

    @Autowired
    public OrderServiceImpl(
            @Qualifier("orderRepository") final OrderRepository orderRepository,
            IkuSportsConfig config,
            @Qualifier("userService") final UserService userService) {
        this.orderRepository = orderRepository;
        this.config = config;
        this.userService = userService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public Order saveAndReturn(NewOrderRequest request) throws ApiServiceException, DataAccessException {
        final String orderNo = Utils.genUniqueStr();
        User user = userService.getUserById(request.getToken());

        Order order = Order.builder()
                .orderId(orderNo)
                .userId((user == null) ? null : user.getId())
                .courseId(request.getCourseId())
                .discount(request.getDiscount())
                .fee((int) (request.getFee() * 100))
                .build();

        orderRepository.save(order);
        return order;
    }

    @Override
    public Order getOrderById(@NotNull String orderId) throws ApiServiceException {
        try {
            return Optional.ofNullable(orderRepository.findOrderById(orderId))
                    .map(o -> {
                        o.setMoneyPaid((int) (o.getFee() * o.getDiscount()));
                        return o;
                    })
                    .orElseThrow(() -> new ApiServiceException(IkuSportsError.ORDER_NOT_FOUND_ERROR));
        } catch (DataAccessException e) {
            log.error("Failed to retrieve order by id: {}", orderId, e);
            throw new ApiServiceException(IkuSportsError.REQ_RESOURCE_NOT_FOUND_ERR);
        }
    }
}
