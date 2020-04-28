/*
 * File: WatchedClassHisRepository
 * Author: DorSey Q F TANG
 * Created: 2020/4/28
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.repository;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository("watchedClassHisRepository")
public interface WatchedClassHisRepository {
    String TABLE = "class_watched_his";

    @SelectProvider(type = WatchedClassHisSQLProvider.class, method = "countByUserIdAndClassId")
    int countByUserIdAndClassId(@Param("userId") String userId, @Param("classId") int classId) throws
            DataAccessException;

    // -------
    // SQL provider
    // -------
    class WatchedClassHisSQLProvider {
        public String countByUserIdAndClassId(final Map<String, Object> params) {
            return new SQL() {
                {
                    SELECT("COUNT(1)");
                    FROM(TABLE);
                    if (params.get("userId") != null) {
                        WHERE("user_id = #{userId}");
                    }

                    WHERE("class_id = #{classId}");
                }
            }.toString();
        }
    }
}
