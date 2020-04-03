/**
 * File: CourseRepository
 * Author: DorSey Q F TANG
 * Created: 2020/3/28 15:09
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.repository;

import com.iku.sports.mini.admin.entity.Course;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository("courseRepository")
public interface CourseRepository {
    String TABLE = "course";

    @Results(id = "courseRM", value = {
            @Result(property = "id", column = "id", jdbcType = JdbcType.INTEGER),
            @Result(property = "level", column = "level", jdbcType = JdbcType.CHAR),
            @Result(property = "fee", column = "fee", jdbcType = JdbcType.BIGINT),
            @Result(property = "name", column = "name", jdbcType = JdbcType.VARCHAR),
            @Result(property = "joiner", column = "joiner", jdbcType = JdbcType.BIGINT),
            @Result(property = "backgroundImg", column = "background_img", jdbcType = JdbcType.VARCHAR)
    })
    @SelectProvider(type = CourseSQLProvider.class, method = "findCoursesByCategoryId")
    List<Course> findCoursesByCategoryId(@Param("categoryId") final short categoryId);

    // -----
    // SQL provider
    // -----
    class CourseSQLProvider {
        static final List<String> COLS = Arrays.asList("id", "name", "joiner", "level", "fee", "background_img");

        public String findCoursesByCategoryId(final Map<String, Object> params) {
            return new SQL() {
                {
                    SELECT(COLS.toArray(new String[COLS.size()]));
                    FROM(TABLE);
                    WHERE("category_id = #{categoryId}");
                }
            }.toString();
        }
    }
}
