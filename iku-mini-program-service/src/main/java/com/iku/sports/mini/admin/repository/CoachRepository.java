package com.iku.sports.mini.admin.repository;

import com.google.common.collect.Lists;
import com.iku.sports.mini.admin.entity.Coach;
import com.iku.sports.mini.admin.model.CoachInfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * File: Coach
 * Author: Huanghz
 * Created: ${date}
 * Description:
 * CopyRight: All rights reserved
 **/
@Repository("coachRepository")
public interface CoachRepository {
    String COACH_TABLE = "coach";
    String COURSE_CLASS_TABLE = "class";

    @Results(id = "coachInfoRM", value = {
            @Result(property = "id", column = "id", jdbcType = JdbcType.INTEGER),
            @Result(property = "name", column = "name", jdbcType = JdbcType.VARCHAR),
            @Result(property = "headingImgUrl", column = "heading_img_url", jdbcType = JdbcType.VARCHAR),
            @Result(property = "title", column = "title", jdbcType = JdbcType.VARCHAR),
            @Result(property = "gender", column = "gender", jdbcType = JdbcType.INTEGER),
            @Result(property = "nationality", column = "nationality", jdbcType = JdbcType.VARCHAR),
            @Result(property = "level", column = "level", jdbcType = JdbcType.INTEGER),
            @Result(property = "introduce", column = "introduce", jdbcType = JdbcType.VARCHAR),
            @Result(property = "numOfCoachClasses", column = "classes", jdbcType = JdbcType.INTEGER)
    })
    @SelectProvider(type = CoachSqlProvider.class, method = "getAllCoachInfos")
    List<CoachInfo> getAllCoachesBriefs();

    @Results(id = "coachRM", value = {
            @Result(property = "id", column = "id", jdbcType = JdbcType.INTEGER),
            @Result(property = "name", column = "name", jdbcType = JdbcType.VARCHAR),
            @Result(property = "headingImgUrl", column = "heading_img_url", jdbcType = JdbcType.VARCHAR),
            @Result(property = "title", column = "title", jdbcType = JdbcType.VARCHAR),
            @Result(property = "gender", column = "gender", jdbcType = JdbcType.INTEGER),
            @Result(property = "age", column = "age", jdbcType = JdbcType.INTEGER),
            @Result(property = "nationality", column = "nationality", jdbcType = JdbcType.VARCHAR),
            @Result(property = "level", column = "level", jdbcType = JdbcType.INTEGER),
            @Result(property = "introduce", column = "introduce", jdbcType = JdbcType.VARCHAR)
    })
    @SelectProvider(type = CoachSqlProvider.class, method = "getCoachById")
    Coach getCoachById(@Param("coachId") int id);

    class CoachSqlProvider {
        static final List<String> SIMPLE_COLS = Arrays.asList("t.name", "t.heading_img_url", "t.title", "t.nationality",
                                                              "t.level");
        static final List<String> SIMPLE_COLS_WITH_COUNT = Lists.newArrayList(SIMPLE_COLS);
        static final List<String> COLS = Arrays.asList("t.id", "t.name", "t.age", "t.heading_img_url", "t.title",
                                                       "t.gender", "t.nationality", "t.level", "t.introduce");

        static {
            SIMPLE_COLS_WITH_COUNT.add("count(t1.id) as classes");
        }

        public String getAllCoachInfos() {

            return new SQL() {
                {
                    SELECT(SIMPLE_COLS_WITH_COUNT.toArray(new String[SIMPLE_COLS_WITH_COUNT.size()]));
                    FROM(COACH_TABLE + " t");
                    LEFT_OUTER_JOIN(COURSE_CLASS_TABLE + " t1 on t.id = t1.coach_id");
                    GROUP_BY(SIMPLE_COLS.toArray(new String[SIMPLE_COLS.size()]));
                    ORDER_BY("t.level desc");
                }
            }.toString();
        }

        public String getCoachById(final Map<String, Object> param) {
            return new SQL() {
                {
                    SELECT(COLS.toArray(new String[COLS.size()]));
                    FROM(COACH_TABLE+" t");
                    WHERE("id = #{coachId}");
                }
            }.toString();
        }
    }


}
