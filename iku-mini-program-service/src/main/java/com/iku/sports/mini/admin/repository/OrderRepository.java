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
    String TABLE = "`order`";
    String TABLE_COURSE = "course";

    @InsertProvider(type = OrderSQLProvider.class, method = "save")
    void save(Order order) throws DataAccessException;

    @UpdateProvider(type = OrderSQLProvider.class, method = "updateTransIdAndPaidTimeById")
    void updateTransIdAndPaidTimeById(@Param("transactionId") String transactionId, @Param("paidTime") Date paidTime,
            @Param("orderId") String orderId) throws DataAccessException;

    @Results(id = "simpleOrder", value = {
            @Result(property = "orderId", column = "id", jdbcType = JdbcType.VARCHAR),
            @Result(property = "fee", column = "fee", jdbcType = JdbcType.BIGINT),
            @Result(property = "discount", column = "discount", jdbcType = JdbcType.FLOAT),
            @Result(property = "moneyPaid", column = "money_paid", jdbcType = JdbcType.BIGINT),
            @Result(property = "paidTime", column = "paid_time"),
            @Result(property = "moneyRefund", column = "money_refund", jdbcType = JdbcType.BIGINT),
            @Result(property = "status", column = "status", jdbcType = JdbcType.CHAR),
            @Result(property = "productId", column = "product_id", jdbcType = JdbcType.VARCHAR),
            @Result(property = "productType", column = "product_type", jdbcType = JdbcType.TINYINT),
            @Result(property = "transactionId", column = "transaction_id", jdbcType = JdbcType.TINYINT),
            @Result(property = "createdTime", column = "create_time"),
            @Result(property = "lastModifyTime", column = "last_modify_time"),
            @Result(property = "userId", column = "user_id", jdbcType = JdbcType.VARCHAR),
    })
    @SelectProvider(type = OrderSQLProvider.class, method = "findOrderByUserId")
    List<Order> findOrderByUserId(@Param("userId") String userId) throws DataAccessException;

    @DeleteProvider(type = OrderSQLProvider.class, method = "deleteById")
    void deleteById(@Param("orderId") String orderId) throws DataAccessException;

    // -----
    // SQL provider
    // -----
    class OrderSQLProvider {
        private final static List<String> ORDER_COLS = Lists.newArrayList("`id`", "`fee`", "`discount`",
                "`money_paid`", "`money_refund`", "`status`", "`product_id`", "`product_type`", "`transaction_id`",
                "`user_id`", "`paid_time`", "`create_time`", "`last_modify_time`");

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

        public String findOrderByUserId(final Map<String, Object> params) {
            return new SQL() {
                {
                    SELECT(ORDER_COLS.toArray(new String[0]));
                    FROM(TABLE);
                    WHERE("user_id = #{userId} and status = 1");
                }
            }.toString();
        }

        public String deleteById(final Map<String, Object> params) {
            return new SQL() {
                {
                    DELETE_FROM(TABLE);
                    if (params.get("orderId") != null) {
                        WHERE("id = #{orderId}");
                    } else {
                        // if no order id found, prevent it from delete all.
                        WHERE("1 = 2");
                    }
                }
            }.toString();
        }
    }
}
