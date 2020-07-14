/*
 * File: OrderServiceImpl
 * Author: DorSey Q F TANG
 * Created: 2020/4/22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service.impl;

import com.google.common.base.Splitter;
import com.iku.sports.mini.admin.config.IkuSportsConfig;
import com.iku.sports.mini.admin.entity.CourseOrder;
import com.iku.sports.mini.admin.entity.Order;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.model.Constants;
import com.iku.sports.mini.admin.model.Paging;
import com.iku.sports.mini.admin.repository.CourseOrderRepository;
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

import java.math.RoundingMode;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service("orderService")
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final IkuSportsConfig config;
    private final CourseOrderRepository courseOrderRepository;

    @Autowired
    public OrderServiceImpl(@Qualifier("orderRepository") OrderRepository orderRepository,
            IkuSportsConfig config,
            @Qualifier("courseOrderRepository") final CourseOrderRepository courseOrderRepository) {
        this.orderRepository = orderRepository;
        this.config = config;
        this.courseOrderRepository = courseOrderRepository;
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
    public void deleteOrderById(final String orderId) throws ApiServiceException {
        orderRepository.deleteById(orderId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public void updateTransIdAndPaidTimeById(String transactionId, Date paidTime, String orderId) throws
            DataAccessException {
        this.orderRepository.updateTransIdAndPaidTimeById(transactionId, paidTime, orderId);
    }

    @Override
    public Paging<CourseOrder> paginateByUserIdAndStatus(int curPage, String userId, char status) {
        final int pageSize = config.getPageSize();
        final Paging<CourseOrder> paging = new Paging<>(curPage, pageSize);
        final List<CourseOrder> courseOrders = courseOrderRepository
                .findPagingOrdersByUserIdAndStatus(paging.getOffset(), pageSize, userId, status);
        courseOrders.forEach(courseOrder -> {
            courseOrder.getFee().divide(Constants.ONE_HUNDRED).setScale(2, RoundingMode.HALF_UP);
            List<String> result = Splitter.on(Constants.DELIM_DOT).splitToList(String.valueOf(courseOrder.getFee()));
            if (result.size() == 2) {
                courseOrder.setIntegerPart(result.get(0));
                courseOrder.setDecimalPart(result.get(1));
            }
        });

        paging.setData(courseOrders);
        return paging;
    }

}