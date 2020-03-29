package com.iku.sports.mini.admin.repository;

import com.iku.sports.mini.admin.entity.ArticleContent;
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

@Repository("articleContentRepository")
public interface ArticleContentRepository {
    String articleContentTable="article_content";

    @Results(id = "articleContentId", value = {
            @Result(property = "id", column = "id", jdbcType = JdbcType.BIGINT),
            @Result(property = "material", column = "material", jdbcType = JdbcType.VARCHAR),
            @Result(property = "materialType", column = "material_type", jdbcType = JdbcType.VARCHAR),
            @Result(property = "createTime", column = "create_time", jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "articleId", column = "article_id", jdbcType = JdbcType.BIGINT)
    })
    @SelectProvider(type = ArticleContentSQLProvider.class, method = "getArticleContentsByArticleId")
    List<ArticleContent> getArticleContentsByArticleId(@Param("articleId") String categoryId,
                                                       @Param("pageNo") int pageNo,
                                                       @Param("pageSize") int pageSize) throws Exception;
    class ArticleContentSQLProvider{
        static final List<String> COLS = Arrays.asList("id", "material", "material_type","create_time","article_id");
        String getArticleContentsByArticleId(final Map<String, Object> params){
            return new SQL(){
                {
                    SELECT(COLS.toArray(new String[COLS.size()]));
                    FROM(articleContentTable);
                    WHERE("article_Id = #{articleId}");
                    ORDER_BY(" create_time desc,article_id asc limit (#{pageNo}-1) * #{pageSize},#{pageSize} ");
                }
            }.toString();
        }
    }
}
