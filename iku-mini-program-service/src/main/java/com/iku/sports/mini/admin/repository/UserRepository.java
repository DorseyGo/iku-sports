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
    String TABLE_FAVORITE = "favorite";

    @InsertProvider(type = UserSQLProvider.class, method = "save")
    void save(User user) throws DataAccessException;

    @Results(id = "simpleUserRM", value = {
            @Result(property = "id", column = "id", javaType = String.class, jdbcType = JdbcType.CHAR),
            @Result(property = "openId", column = "open_id", jdbcType = JdbcType.VARCHAR),
            @Result(property = "avatarUrl", column = "avatar_url", jdbcType = JdbcType.VARCHAR),
            @Result(property = "nickName", column = "name", jdbcType = JdbcType.VARCHAR),
            @Result(property = "telephone", column = "telephone", jdbcType = JdbcType.VARCHAR),
            @Result(property = "numAttentions", column = "num_attentions", jdbcType = JdbcType.INTEGER),
            @Result(property = "numCourses", column = "num_courses", jdbcType = JdbcType.INTEGER)
    })
    @SelectProvider(type = UserSQLProvider.class, method = "findUserByUserId")
    User findUserByUserId(@NotNull @Param("userId") String userId);

    // ----
    // SQL provider
    // ----
    class UserSQLProvider {
        private static List<String> COLS = Lists.newArrayList("u.id", "u.open_id", "u.avatar_url", "u.name", "u.telephone");

        static {
            COLS.add("(SELECT COUNT(f.id) FROM " + TABLE_FAVORITE + " f WHERE f.user_id = u.id AND f.favorite_type = 3) num_attentions");
            COLS.add("(SELECT COUNT(f1.id) FROM " + TABLE_FAVORITE + " f1 WHERE f1.user_id = u.id AND f1.favorite_type = 2) num_courses");
        }

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

        public String findUserByUserId(final Map<String, Object> params) {
            return new SQL() {
                {
                    SELECT(COLS.toArray(new String[COLS.size()]));
                    FROM(TABLE + " u");
                    if (params.get("userId") != null) {
                        WHERE("u.id = #{userId}");
                    }
                }
            }.toString();
        }
    }
}
