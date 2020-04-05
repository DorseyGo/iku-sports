package com.iku.sports.mini.admin.repository;

import com.iku.sports.mini.admin.entity.Article;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository("articleRepository")
public interface ActicleRepository {
    String articleTable="article";

    @Results(id = "articleRM", value = {
            @Result(property = "id", column = "id", jdbcType = JdbcType.BIGINT),
            @Result(property = "title", column = "title", jdbcType = JdbcType.VARCHAR),
            @Result(property = "cover", column = "cover", jdbcType = JdbcType.VARCHAR),
            @Result(property = "createTime", column = "create_time", jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "author", column = "author", jdbcType = JdbcType.VARCHAR),
            @Result(property = "categoryId", column = "category_id", jdbcType = JdbcType.TINYINT)
    })
    @SelectProvider(type = ArticleSQLProvider.class, method = "getArticlesByCategoryId")
    List<Article> getArticlesByCategoryId(@Param("categoryId") String categoryId,
                                             @Param("pageNo") int pageNo,
                                             @Param("pageSize") int pageSize) throws Exception;

    @ResultMap("articleRM")
    @SelectProvider(type = ArticleSQLProvider.class, method = "findFirst3Articles")
    List<Article> findFirst3Articles();


    class ArticleSQLProvider{
        static final List<String> COLS = Arrays.asList("id", "title", "cover","create_time","author","category_id");
        public String getArticlesByCategoryId(final Map<String, Object> params){
            return new SQL(){
                {
                    SELECT(COLS.toArray(new String[COLS.size()]));
                    FROM(articleTable);
                    WHERE("category_id = #{categoryId}");
                    ORDER_BY(" create_time desc limit (#{pageNo}-1) * #{pageSize},#{pageSize} ");
                }
            }.toString();
        }

        public String findFirst3Articles() {
            return new SQL() {
                {
                    SELECT(COLS.toArray(new String[COLS.size()]));
                    FROM(articleTable);
                    ORDER_BY("create_time DESC LIMIT 0,3");
                }
            }.toString();
        }
    }
}
