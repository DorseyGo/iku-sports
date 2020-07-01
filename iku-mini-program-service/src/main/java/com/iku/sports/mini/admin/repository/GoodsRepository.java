package com.iku.sports.mini.admin.repository;

import com.iku.sports.mini.admin.entity.Goods;
import com.iku.sports.mini.admin.model.Paging;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository
public interface GoodsRepository {
    @Results(id = "goodsResult", value = {
            @Result(property = "id", column = "id", jdbcType = JdbcType.INTEGER),
            @Result(property = "name", column = "name", jdbcType = JdbcType.VARCHAR),
            @Result(property = "price", column = "price", jdbcType = JdbcType.INTEGER),
            @Result(property = "markingPrice", column = "marking_price", jdbcType = JdbcType.INTEGER),
            @Result(property = "stock", column = "stock", jdbcType = JdbcType.INTEGER),
            @Result(property = "smallLogo", column = "small_logo", jdbcType = JdbcType.VARCHAR),
            @Result(property = "bigLogo", column = "big_logo", jdbcType = JdbcType.VARCHAR),
            @Result(property = "status", column = "status", jdbcType = JdbcType.TINYINT),
            @Result(property = "desc", column = "desc", jdbcType = JdbcType.VARCHAR),
            @Result(property = "createdAt", column = "created_at", jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "updatedAt", column = "updated_at", jdbcType = JdbcType.TIMESTAMP),
    })
    @SelectProvider(type = GoodsSqlProvider.class, method = "queryPage")
    List<Goods> queryPage(Paging<Goods> paging);

    @SelectProvider(type = GoodsSqlProvider.class, method = "count")
    int count();

    class GoodsSqlProvider {
        private static final String TABLE_NAME = "goods";
        private static final List<String> COLUMNS = Arrays.asList("`id`", "`name`", "`price`",
                "`marking_price`", "`stock`", "`small_logo`", "`big_logo`", "`status`",
                "`desc`", "`created_at`", "`updated_at`");

        public String queryPage(Paging<Goods> paging) {
            return new SQL() {
                {
                    SELECT(COLUMNS.toArray(new String[0]));
                    FROM(TABLE_NAME);
                    WHERE("status = 0");
                    ORDER_BY("created_at DESC LIMIT #{offset}, #{pageSize}");
                }
            }.toString();
        }

        public String count() {
           return "select count(*) from " + TABLE_NAME;
        }
    }
}
