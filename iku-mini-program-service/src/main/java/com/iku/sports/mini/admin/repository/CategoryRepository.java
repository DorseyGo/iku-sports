/**
 * File: CategoryRepository
 * Author: DorSey Q F TANG
 * Created: 2020/3/19
 * CopyRight: All rights reserved
 **/
package com.iku.sports.mini.admin.repository;

import com.iku.sports.mini.admin.entity.Category;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository("categoryRepository")
public interface CategoryRepository {

    String TABLE = "category";

    @Results(id = "categoryRM", value = {
            @Result(property = "id", column = "id", jdbcType = JdbcType.TINYINT),
            @Result(property = "name", column = "name", jdbcType = JdbcType.VARCHAR),
            @Result(property = "displayName", column = "display_name", jdbcType = JdbcType.VARCHAR),
            @Result(property = "avatar", column = "avatar", jdbcType = JdbcType.VARCHAR),
            @Result(property = "sequence", column = "sequence", jdbcType = JdbcType.TINYINT)
    })
    @SelectProvider(type = CategorySQLProvider.class, method = "findAll")
    List<Category> findAll() throws DataAccessException;

    // ---
    // SQL provider
    // ---
    class CategorySQLProvider {
        static final List<String> COLS = Arrays.asList("id", "name", "display_name", "avatar", "sequence");

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
