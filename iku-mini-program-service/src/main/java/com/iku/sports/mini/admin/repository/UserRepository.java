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

    @Results(id = "simpleUserRM", value = {
            @Result(property = "id", column = "id", javaType = String.class, jdbcType = JdbcType.CHAR),
            @Result(property = "openId", column = "open_id", jdbcType = JdbcType.VARCHAR),
            @Result(property = "token", column = "token", javaType = String.class, jdbcType = JdbcType.CHAR)
    })
    @SelectProvider(type = UserSQLProvider.class, method = "findUserByToken")
    User findUserByToken(@NotNull @Param("token") String token);

    // ----
    // SQL provider
    // ----
    class UserSQLProvider {
        private static List<String> COLS = Lists.newArrayList("id", "open_id", "token");

        public String save(final User user) {
            return new SQL() {
                {
                    INSERT_INTO(TABLE);
                    if (user.getId() != null) {
                        VALUES("id", "#{id}");
                    }

                    if (user.getOpenId() != null) {
                        VALUES("open_id", "#{openId}");
                    }

                    if (user.getToken() != null) {
                        VALUES("token", "#{token}");
                    }

                    if (user.getAvatarUrl() != null) {
                        VALUES("avatar_url", "#{avatarUrl}");
                    }

                    if (user.getNickName() != null) {
                        VALUES("name", "#{nickName}");
                    }

                    if (user.getProvince() != null) {
                        VALUES("province", "#{province}");
                    }

                    if (user.getCity() != null) {
                        VALUES("city", "#{city}");
                    }

                    if (user.getCountry() != null) {
                        VALUES("country", "#{country}");
                    }

                    VALUES("gender", "#{gender}");
                }
            }.toString();
        }

        public String findUserByToken(final Map<String, Object> params) {
            return new SQL() {
                {
                    SELECT(COLS.toArray(new String[COLS.size()]));
                    FROM(TABLE);
                    WHERE("token = #{token}");
                }
            }.toString();
        }
    }
}
