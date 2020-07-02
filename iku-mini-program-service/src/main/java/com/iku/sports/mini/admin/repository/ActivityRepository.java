/**
 * File: ActivityRepository
 * Author: DorSey Q F TANG
 * Created: 2020/3/19
 * CopyRight: All rights reserved
 **/
package com.iku.sports.mini.admin.repository;

import com.iku.sports.mini.admin.entity.Activity;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository("activityRepository")
public interface ActivityRepository {

    String TABLE = "activity";

    @Results(id = "activityRM", value = {
            @Result(property = "id", column = "id", jdbcType = JdbcType.TINYINT),
            @Result(property = "image", column = "image", jdbcType = JdbcType.VARCHAR),
            @Result(property = "title", column = "title", jdbcType = JdbcType.VARCHAR),
            @Result(property = "link", column = "link", jdbcType = JdbcType.VARCHAR)
    })
    @SelectProvider(type = ActivitySQLProvider.class, method = "findFirst3ByOrderByCreateTimeDesc")
    List<Activity> findFirst3ByOrderByCreateTimeDesc() throws DataAccessException;

    @ResultMap("activityRM")
    @SelectProvider(type = ActivitySQLProvider.class, method = "findFirst3ByOrderByCreateTimeDesc")
    List<Activity> findFirst3ByCategoryIdOrderByCreateTimeDesc(@Param("categoryId") final short categoryId) throws SQLException;

    // -----
    // SQL provider
    // -----
    class ActivitySQLProvider {
        static final List<String> COLS = Arrays.asList("id", "title", "image", "link");

        public String findFirst3ByOrderByCreateTimeDesc(final Map<String, Object> params) {
            return new SQL() {
                {
                    SELECT(COLS.toArray(new String[COLS.size()]));
                    FROM(TABLE);
                    if (params != null && params.get("categoryId") != null) {
                        WHERE("category_id = #{categoryId}");
                    }

                    ORDER_BY("create_time DESC LIMIT 0,3");
                }
            }.toString();
        }

    }
}
