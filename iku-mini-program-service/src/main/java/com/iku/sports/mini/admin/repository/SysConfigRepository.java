/**
 * File: SysConfigRepository
 * Author: DorSey Q F TANG
 * Created: 2020/7/3 22:18
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.repository;

import com.iku.sports.mini.admin.entity.SysConfig;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;
import org.assertj.core.util.Lists;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("sysConfigRepository")
public interface SysConfigRepository {

    String TABLE = "sys_config";

    @Results(id = "sysConfigRM", value = {
            @Result(property = "key", column = "sys_key", jdbcType = JdbcType.VARCHAR),
            @Result(property = "value", column = "sys_value", jdbcType = JdbcType.VARCHAR)
    })
    @SelectProvider(type = SysConfigSQLProvider.class, method = "findSysConfigByKey")
    SysConfig findSysConfigByKey(@Param("key") final String key) throws DataAccessException;

    // ----
    // sql provider
    // ----
    class SysConfigSQLProvider {
        static final List<String> COLS = Lists.newArrayList("sys_key", "sys_value");

        public String findSysConfigByKey(final Map<String, Object> params) {
            return new SQL() {
                {
                    SELECT(COLS.toArray(new String[COLS.size()]));
                    FROM(TABLE);
                    WHERE("sys_key = #{key}");
                }
            }.toString();
        }
    }
}
