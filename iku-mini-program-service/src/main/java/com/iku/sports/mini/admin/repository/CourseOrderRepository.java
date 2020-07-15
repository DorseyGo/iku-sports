/*
 * File: CourseOrderRepository
 * Author: DorSey Q F TANG
 * Created: 2020/7/14
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.repository;

import com.google.common.collect.Lists;
import com.iku.sports.mini.admin.entity.CourseOrder;
import com.iku.sports.mini.admin.model.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("courseOrderRepository")
public interface CourseOrderRepository {

    String TABLE = "v_order_course";

    @Results(id = "courseOrderRM", value = {
            @Result(property = "orderId", column = "id", jdbcType = JdbcType.VARCHAR),
            @Result(property = "userId", column = "user_id", jdbcType = JdbcType.VARCHAR),
            @Result(property = "name", column = "name", jdbcType = JdbcType.VARCHAR),
            @Result(property = "fee", column = "fee", jdbcType = JdbcType.BIGINT),
            @Result(property = "courseDescription", column = "description", jdbcType = JdbcType.VARCHAR),
            @Result(property = "status", column = "status", jdbcType = JdbcType.CHAR),
            @Result(property = "category", column = "category", jdbcType = JdbcType.VARCHAR),
            @Result(property = "level", column = "level", jdbcType = JdbcType.CHAR)
    })
    @SelectProvider(type = CourseOrderSQLProvider.class, method = "findPagingOrdersByUserIdAndStatus")
    List<CourseOrder> findPagingOrdersByUserIdAndStatus(@Param("offset") int offset, @Param("limit") int pageSize,
            @Param("userId") String userId, @Param("status") char status);


    // ---------------
    // SQL provider
    // ---------------
    class CourseOrderSQLProvider {
        static final List<String> COLS = Lists
                .newArrayList("id", "user_id", "name", "fee", "description", "status", "category", "level");

        public String findPagingOrdersByUserIdAndStatus(final Map<String, Object> params) {
            return new SQL() {
                {
                    SELECT(COLS.toArray(new String[COLS.size()]));
                    FROM(TABLE);
                    if (params.get("userId") != null) {
                        WHERE("user_id = #{userId}");
                    }

                    if (params.get("status") != null && ((Character) params.get(
                            "status")).charValue() != Constants.OrderStatus.ANY.getCode()) {
                        WHERE("status = #{status}");
                    }

                    ORDER_BY("create_time DESC LIMIT #{offset},#{limit}");
                }
            }.toString();
        }
    }
}
