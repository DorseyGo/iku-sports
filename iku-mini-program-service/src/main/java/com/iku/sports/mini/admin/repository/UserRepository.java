/**
 * File: UserRepository
 * Author: DorSey Q F TANG
 * Created: 2020/4/19 14:36
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.repository;

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

    // ----
    // SQL provider
    // ----
    class UserSQLProvider {

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

    }
}
