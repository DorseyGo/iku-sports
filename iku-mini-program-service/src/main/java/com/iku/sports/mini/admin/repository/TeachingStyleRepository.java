/*
 * File: TeachingStyleRepository
 * Author: DorSey Q F TANG
 * Created: 2020/7/16
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.repository;

import com.google.common.collect.Lists;
import com.iku.sports.mini.admin.entity.TeachingStyle;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("teachingStyleRepository")
public interface TeachingStyleRepository {

    String TABLE = "teaching_style";

    @Results(id = "teachingStyleRM", value = {
            @Result(property = "id", column = "id", jdbcType = JdbcType.VARCHAR),
            @Result(property = "title", column = "title", jdbcType = JdbcType.VARCHAR),
            @Result(property = "cover", column = "cover", jdbcType = JdbcType.VARCHAR),
            @Result(property = "tags", column = "labels", jdbcType = JdbcType.VARCHAR),
            @Result(property = "video", column = "video", jdbcType = JdbcType.VARCHAR)
    })
    @SelectProvider(type = TeachingStyleSQLProvider.class, method = "findAll")
    List<TeachingStyle> findAll() throws DataAccessException;

    // ------
    // SQL provider
    // ------
    class TeachingStyleSQLProvider {
        static final List<String> COLS = Lists.newArrayList("id", "title", "cover", "labels", "video");

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
