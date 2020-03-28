/**
 * File: ActivityRepository
 * Author: DorSey Q F TANG
 * Created: 2020/3/19
 * CopyRight: All rights reserved
 **/
package com.iku.sports.mini.admin.repository;

import com.iku.sports.mini.admin.entity.Activity;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository("activityRepository")
public interface ActivityRepository {

    String TABLE = "activity";

    @Results(id = "activityRM", value = {
            @Result(property = "id", column = "id", jdbcType = JdbcType.TINYINT),
            @Result(property = "image", column = "image", jdbcType = JdbcType.VARCHAR),
            @Result(property = "link", column = "link", jdbcType = JdbcType.VARCHAR)
    })
    @SelectProvider(type = ActivitySQLProvider.class, method = "findFirst3ByOrderByCreateTimeDesc")
    List<Activity> findFirst3ByOrderByCreateTimeDesc();

    @ResultMap("activityRM")
    List<Activity> findFirst3ByCategoryIdOrderByCreateTimeDesc(final short categoryId);

    // -----
    // SQL provider
    // -----
    class ActivitySQLProvider {
        static final List<String> COLS = Arrays.asList("id", "image", "link");

        public String findFirst3ByOrderByCreateTimeDesc() {
            return new SQL() {
                {
                    SELECT(COLS.toArray(new String[COLS.size()]));
                    FROM(TABLE);
                    ORDER_BY("create_time DESC LIMIT 0,3");
                }
            }.toString();
        }

        public String findFirst3ByCategoryIdOrderByCreateTimeDesc(final Map<String, Object> params) {
            return new SQL() {
                {
                    SELECT(COLS.toArray(new String[COLS.size()]));
                    FROM(TABLE);
                    WHERE("category_id = #{categoryId}");
                    ORDER_BY("create_time DESC LIMIT 0,3");
                }
            }.toString();
        }
    }
}
