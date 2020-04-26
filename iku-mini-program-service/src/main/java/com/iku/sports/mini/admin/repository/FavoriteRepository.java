package com.iku.sports.mini.admin.repository;

import com.iku.sports.mini.admin.entity.Favorite;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * File: FavoriteRepository
 * Author: Huanghz
 * Created: 2020/4/18
 * Description:
 * CopyRight: All rights reserved
 **/
@Repository("favoriteRepository")
public interface FavoriteRepository {

    String TABLE = "favorite";

    @InsertProvider(type = FavoriteSQLProvider.class, method = "insert")
    void insert(@Param("userId") String userId, @Param("favoriteId") int favoriteId,
            @Param("favoriteType") int favoriteType) throws DataAccessException;

    //SQL provider
    class FavoriteSQLProvider {
        static final List<String> ALL_COLS = Arrays.asList("id", "user_id", "favorite_id", "favorite_type");

        public String insert(final Map<String, Object> params) {
            return new SQL() {
                {
                    INSERT_INTO(TABLE);
                    if (params.get("userId") != null) {
                        VALUES("user_id", "#{userId}");
                    }

                    VALUES("favorite_id", "#{favoriteId}");
                    VALUES("favorite_type", "#{favoriteType}");
                }
            }.toString();

        }

    }

}