/**
 * File: UserRepository
 * Author: DorSey Q F TANG
 * Created: 2020/4/19 14:36
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.repository;

import com.google.common.collect.Lists;
import com.iku.sports.mini.admin.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;
import org.checkerframework.checker.guieffect.qual.SafeEffect;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Repository("userRepository")
public interface UserRepository {

    String TABLE = "user";

    @InsertProvider(type = UserSQLProvider.class, method = "save")
    void save(User user) throws DataAccessException;

    @Results(id = "userRM", value = {
            @Result(property = "id", column = "id", jdbcType = JdbcType.VARCHAR),
            @Result(property = "openId", column = "open_id", jdbcType = JdbcType.VARCHAR),
            @Result(property = "name", column = "name", jdbcType = JdbcType.VARCHAR),
            @Result(property = "avatar", column = "avatar_url", jdbcType = JdbcType.VARCHAR),
            @Result(property = "telephone", column = "telephone", jdbcType = JdbcType.VARCHAR),
            @Result(property = "gender", column = "gender", jdbcType = JdbcType.CHAR)
    })
    @SelectProvider(type = UserSQLProvider.class, method = "findUserById")
    User findUserById(@Param("id") String userId) throws DataAccessException;

    // ----
    // SQL provider
    // ----
    class UserSQLProvider {

        static final List<String> COLS = Lists.newArrayList("id", "open_id", "name", "avatar_url", "gender", "telephone");

        public String save(final User user) {
            return new SQL() {
                {
                    INSERT_INTO(TABLE);
                    if (user.getId() != null) {
                        VALUES("id", "#{id}");
                    }

                    if (user.getOpenId() !=  null) {
                        VALUES("open_id", "#{openId}");
                    }

                    if (user.getSessionKey() != null) {
                        VALUES("session_key", "#{sessionKey}");
                    }
                }
            }.toString();
        }

        public String findUserById(final Map<String, Object> params) {
            return new SQL() {
                {
                    SELECT(COLS.toArray(new String[COLS.size()]));
                    FROM(TABLE);
                    WHERE("id = #{id}");
                }
            }.toString();
        }

    }
}
