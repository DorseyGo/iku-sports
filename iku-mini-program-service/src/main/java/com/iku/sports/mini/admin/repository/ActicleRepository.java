package com.iku.sports.mini.admin.repository;

import com.iku.sports.mini.admin.entity.Article;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository("articleRepository")
public interface ActicleRepository {
    String articleTable="ARTICLE";

    @Results(id = "articleId", value = {
            @Result(property = "id", column = "id", jdbcType = JdbcType.BIGINT),
            @Result(property = "title", column = "title", jdbcType = JdbcType.VARCHAR),
            @Result(property = "cover", column = "cover", jdbcType = JdbcType.VARCHAR),
            @Result(property = "createTime", column = "create_time", jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "author", column = "author", jdbcType = JdbcType.VARCHAR),
            @Result(property = "categoryId", column = "category_id", jdbcType = JdbcType.VARCHAR)
    })
    @SelectProvider(type = ArticleSQLProvider.class, method = "getArticlesByCategoryId")
    List<Article> getArticlesByCategoryId(@Param("categoryId") String categoryId,
                                             @Param("pageNo") int pageNo,
                                             @Param("pageSize") int pageSize) throws Exception;


    class ArticleSQLProvider{
        static final List<String> COLS = Arrays.asList("id", "title", "cover","create_time","author","category_id");
        String getArticlesByCategoryId(final Map<String, Object> params){
            return new SQL(){
                {
                    SELECT(COLS.toArray(new String[COLS.size()]));
                    FROM(articleTable);
                    WHERE("category_id = #{categoryId}");
                    ORDER_BY(" create_time desc limit (#{pageNo}-1) * #{pageSize},#{pageSize} ");
                }
            }.toString();
        }
    }
}
