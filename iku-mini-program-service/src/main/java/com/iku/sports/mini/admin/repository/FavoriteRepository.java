package com.iku.sports.mini.admin.repository;

import com.iku.sports.mini.admin.entity.Favorite;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;
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

    @InsertProvider(type = FavoriteSQLProvider.class, method = "addFavorite")
    void addFavorite(@Param("favoriteId") int favoriteId,
                    @Param("favoriteType") int favoriteType,
                    @Param("userId") String userId) throws Exception;

    @DeleteProvider(type = FavoriteSQLProvider.class, method = "delFavorite")
    void delFavorite(@Param("favoriteId") int favoriteId,
                     @Param("favoriteType") int favoriteType,
                     @Param("userId") String userId) throws Exception;

    @Results(id= "FavoriteRM",value = {
            @Result(property = "id",column = "id",jdbcType = JdbcType.INTEGER),
            @Result(property = "userId",column = "user_id",jdbcType = JdbcType.INTEGER),
            @Result(property = "favoriteId",column = "favorite_id",jdbcType = JdbcType.INTEGER),
            @Result(property = "favoriteType",column = "favorite_type",jdbcType = JdbcType.INTEGER)
    })
    @SelectProvider(type = FavoriteSQLProvider.class, method = "getFavoriteByUserId")
    List<Favorite> getFavoriteByStudentId(@Param("userId") String userId);

    @ResultMap("FavoriteRM")
    @SelectProvider(type = FavoriteSQLProvider.class, method = "getFavoriteByUserIdFavoriteType")
    List<Favorite> getFavoriteByStudentIdFavoriteType(@Param("userId") String userId,@Param("favoriteType") int favoriteType);

    @SelectProvider(type = FavoriteSQLProvider.class, method = "getFavoriteSummary")
    Integer getFavoriteSummary(@Param("favoriteId") int favoriteId,
                               @Param("favoriteType") int favoriteType,
                               @Param("userId") String userId);

    //SQL provider
    class FavoriteSQLProvider {
        static String TABLE = "favorite";

        static final List<String> ALL_COLS = Arrays.asList("id","user_id","favorite_id","favorite_type");

        public String getFavoriteSummary(final Map<String, Object> param){
            return new SQL(){
                {
                    SELECT("IFNULL(count(1),0)");
                    FROM(TABLE);
                    WHERE("user_id = #{userId}");
                    WHERE("favorite_id = #{favoriteId}");
                    WHERE("favorite_type = #{favoriteType}");
                }
            }.toString();
        }

        public String getFavoriteByUserIdFavoriteType(final Map<String, Object> param){
            return new SQL(){
                {
                    SELECT(ALL_COLS.toArray(new String[ALL_COLS.size()]));
                    FROM(TABLE);
                    WHERE("user_id = #{userId}");
                    WHERE("favorite_type = #{favoriteType}");
                }
            }.toString();
        }

        public String getFavoriteByUserId(final Map<String, Object> param){
            return new SQL(){
                {
                    SELECT(ALL_COLS.toArray(new String[ALL_COLS.size()]));
                    FROM(TABLE);
                    WHERE("user_id = #{userId}");

                }
            }.toString();
        }

        public String addFavorite(final Map<String, Object> params){

            return new SQL(){
                {
                    INSERT_INTO(TABLE);
                    VALUES("user_id","#{userId}");
                    VALUES("favorite_id","#{favoriteId}");
                    VALUES("favorite_type","#{favoriteType}");
                }
            }.toString();

        }

        public String delFavorite(final Map<String, Object> params){
            return new SQL(){
                {
                    DELETE_FROM(TABLE);
                    WHERE("user_id = #{userId}");
                    WHERE("favorite_id = #{favoriteId}");
                    WHERE("favorite_type = #{favoriteType}");
                }
            }.toString();
        }
    }

}