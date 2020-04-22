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

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Repository("orderRepository")
public interface OrderRepository {
    String TABLE = "order";
    String TABLE_COURSE = "course";

    @InsertProvider(type = OrderSQLProvider.class, method = "save")
    void save(Order order) throws DataAccessException;

    @Results(id = "orderRM", value = {
            @Result(property = "orderId", column = "id", jdbcType = JdbcType.VARCHAR),
            @Result(property = "fee", column = "fee", jdbcType = JdbcType.BIGINT),
            @Result(property = "discount", column = "discount", jdbcType = JdbcType.FLOAT),
            @Result(property = "productName", column = "name", jdbcType = JdbcType.VARCHAR)
    })
    @SelectProvider(type = OrderSQLProvider.class, method = "findOrderById")
    Order findOrderById(@NotNull @Param("orderId") String orderId) throws DataAccessException;

    // -----
    // SQL provider
    // -----
    class OrderSQLProvider {
        static final List<String> COLS = Lists.newArrayList("o.id", "o.fee", "o.discount", "c.name");

        public String save(final Order order) {
            return new SQL() {
                {
                    INSERT_INTO(TABLE);
                    if (order.getOrderId() != null) {
                        VALUES("id", "#{orderId}");
                    }

                    VALUES("fee", "#{fee}");
                    VALUES("discount", "#{discount}");
                    VALUES("status", "#{status}");
                    VALUES("course_id", "#{courseId}");

                    if (order.getUserId() != null) {
                        VALUES("user_id", "#{userId}");
                    }
                }
            }.toString();
        }

        public String findOrderById(final Map<String, Object> params) {
            return new SQL() {
                {
                    SELECT(COLS.toArray(new String[COLS.size()]));
                    FROM(TABLE + " o");
                    INNER_JOIN(TABLE_COURSE + " c ON o.course_id = c.id");
                    WHERE("o.id = #{orderId}");
                }
            }.toString();
        }
    }
}
