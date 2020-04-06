/**
 * File: CourseRepository
 * Author: DorSey Q F TANG
 * Created: 2020/3/28 15:09
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.repository;

import com.iku.sports.mini.admin.entity.Course;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository("courseRepository")
public interface CourseRepository {
    String TABLE = "course c";
    String TABLE_CATEGORY = "category t";

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

    @ResultMap("courseRM")
    @SelectProvider(type = CourseSQLProvider.class, method = "findCoursesByCategoryName")
    List<Course> findCoursesByCategoryName(@Param("categoryName") String categoryName) throws SQLException;

    @ResultMap("courseRM")
    @SelectProvider(type = CourseSQLProvider.class, method = "findCourseById")
    Course findCourseById(@Param("courseId") final short courseId) throws SQLException;

    // -----
    // SQL provider
    // -----
    class CourseSQLProvider {
        static final List<String> COLS = Arrays.asList("c.id", "c.name", "c.joiner", "c.level", "c.fee", "c.background_img");

        public String findCoursesByCategoryId(final Map<String, Object> params) {
            return new SQL() {
                {
                    SELECT(COLS.toArray(new String[COLS.size()]));
                    FROM(TABLE);
                    WHERE("c.category_id = #{categoryId}");
                }
            }.toString();
        }

        public String findCoursesByCategoryName(final Map<String, Object> params) {
            return new SQL() {
                {
                    SELECT(COLS.toArray(new String[COLS.size()]));
                    FROM(TABLE);
                    INNER_JOIN(TABLE_CATEGORY + " ON c.category_id = t.id");
                    if (params.get("categoryName") != null) {
                        WHERE("t.name = #{categoryName}");
                    }
                }
            }.toString();
        }

        public String findCourseById(final Map<String, Object> params) {
            return new SQL() {
                {
                    SELECT(COLS.toArray(new String[COLS.size()]));
                    FROM(TABLE);
                    WHERE("id = #{courseId}");
                }
            }.toString();
        }
    }
}
