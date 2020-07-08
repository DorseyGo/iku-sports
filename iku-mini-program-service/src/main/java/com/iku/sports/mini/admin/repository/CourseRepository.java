/**
 * File: CourseRepository
 * Author: DorSey Q F TANG
 * Created: 2020/3/28 15:09
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.repository;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.iku.sports.mini.admin.entity.Course;
import com.iku.sports.mini.admin.model.Constants;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository("courseRepository")
public interface CourseRepository {
    String TABLE = "course c";
    String TABLE_CLASS = "class cl";
    String TABLE_ORDER = "order o";

    @Results(id = "courseRM", value = {
            @Result(property = "id", column = "id", jdbcType = JdbcType.TINYINT),
            @Result(property = "name", column = "name", jdbcType = JdbcType.VARCHAR),
            @Result(property = "level", column = "level", jdbcType = JdbcType.CHAR),
            @Result(property = "fee", column = "fee_in_yuan", jdbcType = JdbcType.DOUBLE),
            @Result(property = "description", column = "description", jdbcType = JdbcType.VARCHAR),
            @Result(property = "categoryId", column = "category_id", jdbcType = JdbcType.TINYINT),
            @Result(property = "numClasses", column = "num_classes", jdbcType = JdbcType.INTEGER)
    })
    @SelectProvider(type = CourseSQLProvider.class, method = "findCoursesByCategoryId")
    List<Course> findCoursesByCategoryId(@Param("categoryId") final short categoryId);

    @ResultMap("courseRM")
    @SelectProvider(type = CourseSQLProvider.class, method = "findCourseById")
    Course findCourseById(@Param("courseId") final short courseId) throws DataAccessException;

    @ResultMap("courseRM")
    @SelectProvider(type = CourseSQLProvider.class, method = "findCoursesByIds")
    List<Course> findCoursesByIds(@Param("courseIds") List<Short> courseIds);

    // -----
    // SQL provider
    // -----
    class CourseSQLProvider {

        static final List<String> COLS = Lists
                .newArrayList("c.id", "c.name", "c.level", "c.fee/100 fee_in_yuan", "c.description", "c.category_id");
        static final List<String> AGG_COLS = Lists.newArrayList(COLS);

        static {
            AGG_COLS.add("COUNT(cl.id) num_classes");
            AGG_COLS.add(new SQL() {
                {
                    SELECT("COUNT(o.user_id)");
                    FROM(TABLE_ORDER);
                    WHERE("o.product_id = c.id");
                    WHERE("o.product_type = " + Constants.ProductType.COURSE.getCode());
                }
            }.toString());
        }

        public String findCoursesByCategoryId(final Map<String, Object> params) {
            return new SQL() {
                {
                    SELECT(AGG_COLS.toArray(new String[AGG_COLS.size()]));
                    FROM(TABLE);
                    LEFT_OUTER_JOIN(TABLE_CLASS + " ON c.id = cl.course_id");
                    WHERE("c.category_id = #{categoryId}");
                    GROUP_BY("c.id");
                }
            }.toString();
        }

        public String findCourseById(final Map<String, Object> params) {
            return new SQL() {
                {
                    SELECT(AGG_COLS.toArray(new String[AGG_COLS.size()]));
                    FROM(TABLE);
                    LEFT_OUTER_JOIN(TABLE_CLASS + " ON c.id = cl.course_id");
                    WHERE("c.id = #{courseId}");
                    GROUP_BY("c.id");
                }
            }.toString();
        }

        public String findCoursesByIds(final Map<String, Object> params) {
            return new SQL() {
                {
                    SELECT(AGG_COLS.toArray(new String[AGG_COLS.size()]));
                    FROM(TABLE);
                    LEFT_OUTER_JOIN(TABLE_CLASS + " ON c.id = cl.course_id");

                    final List<Short> courseIds = (List<Short>) params.get("courseIds");
                    if (courseIds != null && !courseIds.isEmpty()) {
                        final String conditions = Joiner.on(Constants.DELIM_COMMA).join(courseIds);
                        WHERE("(" + conditions + ")");
                    }

                    GROUP_BY("c.id");
                }
            }.toString();
        }
    }
}
