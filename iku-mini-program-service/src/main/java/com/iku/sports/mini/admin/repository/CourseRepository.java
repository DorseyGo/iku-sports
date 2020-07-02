/**
 * File: CourseRepository
 * Author: DorSey Q F TANG
 * Created: 2020/3/28 15:09
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.repository;

import com.google.common.collect.Lists;
import com.iku.sports.mini.admin.entity.Course;
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

    @Results(id = "courseRM", value = {
            @Result(property = "id", column = "id", jdbcType = JdbcType.TINYINT),
            @Result(property = "name", column = "name", jdbcType = JdbcType.VARCHAR),
            @Result(property = "level", column = "level", jdbcType = JdbcType.CHAR),
            @Result(property = "fee", column = "fee_in_yuan", jdbcType = JdbcType.DOUBLE),
            @Result(property = "numClasses", column = "num_classes", jdbcType = JdbcType.INTEGER)
    })
    @SelectProvider(type = CourseSQLProvider.class, method = "findCoursesByCategoryId")
    List<Course> findCoursesByCategoryId(@Param("categoryId") final short categoryId);

    // -----
    // SQL provider
    // -----
    class CourseSQLProvider {

        static final List<String> COLS = Lists.newArrayList("c.id", "c.name", "c.level", "c.fee/100 fee_in_yuan");

        public String findCoursesByCategoryId(final Map<String, Object> params) {
            final List<String> REQ_COLS = Lists.newArrayList(COLS);
            REQ_COLS.add("COUNT(cl.id) num_classes");

            return new SQL() {
                {
                    SELECT(REQ_COLS.toArray(new String[REQ_COLS.size()]));
                    FROM(TABLE);
                    INNER_JOIN(TABLE_CLASS + " ON c.id = cl.course_id");
                    WHERE("c.category_id = #{categoryId}");
                    GROUP_BY("c.id");
                }
            }.toString();
        }
    }
}
