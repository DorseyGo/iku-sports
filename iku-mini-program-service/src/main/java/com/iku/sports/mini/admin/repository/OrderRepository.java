/*
 * File: OrderRepository
 * Author: DorSey Q F TANG
 * Created: 2020/4/22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.repository;

import com.google.common.collect.Lists;
import com.iku.sports.mini.admin.entity.Order;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PatchMapping;

import javax.validation.constraints.NotNull;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository("orderRepository")
public interface OrderRepository {
    String TABLE = "order";

    @InsertProvider(type = OrderSQLProvider.class, method = "save")
    void save(Order order) throws DataAccessException;

    @UpdateProvider(type = OrderSQLProvider.class, method = "updateTransIdAndPaidTimeById")
    void updateTransIdAndPaidTimeById(@Param("transactionId") String transactionId, @Param("paidTime") Date paidTime,
            @Param("orderId") String orderId) throws DataAccessException;

    // -----
    // SQL provider
    // -----
    class OrderSQLProvider {

        public String save(final Order order) {
            return new SQL() {
                {
                    INSERT_INTO(TABLE);
                    if (order.getOrderId() != null) {
                        VALUES("id", "#{orderId}");
                    }

                    VALUES("fee", "#{fee}");
                    VALUES("status", "#{status}");
                    VALUES("product_type", "#{productType}");

                    if (order.getDiscount() != null) {
                        VALUES("discount", "#{discount}");
                    }

                    if (order.getProductId() != null) {
                        VALUES("product_id", "#{productId}");
                    }

                    if (order.getUserId() != null) {
                        VALUES("user_id", "#{userId}");
                    }
                }
            }.toString();
        }

        public String updateTransIdAndPaidTimeById(final Map<String, Object> params) {
            return new SQL() {
                {
                    UPDATE(TABLE);
                    if (params.get("transactionId") != null) {
                        SET("transaction_id", "#{transactionId}");
                    }

                    if (params.get("paidTime") != null) {
                        SET("paid_time", "#{paidTime}");
                    }

                    if (params.get("orderId") != null) {
                        WHERE("id", "#{orderId}");
                    }
                }
            }.toString();
        }
    }
}
