/**
 * File: CourseRepository
 * Author: DorSey Q F TANG
 * Created: 2020/3/28 15:09
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.repository;

import com.iku.sports.mini.admin.entity.Course;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository("courseRepository")
public interface CourseRepository {
    String TABLE = "course";

    @Results(id = "courseRM", value = {
            @Result(property = "id", column = "id", jdbcType = JdbcType.INTEGER),
            @Result(property = "level", column = "level", jdbcType = JdbcType.CHAR),
            @Result(property = "fee", column = "fee", jdbcType = JdbcType.BIGINT)
    })
    @SelectProvider(type = CourseSQLProvider.class, method = "findAll")
    List<Course> findAll();

    // -----
    // SQL provider
    // -----
    class CourseSQLProvider {
        static final List<String> COLS = Arrays.asList("id", "level", "fee");

        public String findAll() {
            return new SQL() {
                {
                    SELECT(COLS.toArray(new String[COLS.size()]));
                    FROM(TABLE);
                }
            }.toString();
        }
    }
}
