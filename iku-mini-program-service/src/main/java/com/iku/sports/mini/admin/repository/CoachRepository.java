package com.iku.sports.mini.admin.repository;

import com.iku.sports.mini.admin.entity.Coach;
import com.iku.sports.mini.admin.model.CoachInfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * File: Coach
 * Author: Huanghz
 * Created: ${date}
 * Description:
 * CopyRight: All rights reserved
 **/
@Repository("coachRepository")
public interface CoachRepository {
    String COACHTABLE = "coach";
    String COURSECLASSTABLE = "class";

    @Results(id = "coachRM",value = {
            @Result(property = "id",column ="id",jdbcType = JdbcType.INTEGER),
            @Result(property = "name",column = "name",jdbcType = JdbcType.VARCHAR),
            @Result(property = "heading_img_url",column = "heading_img_url",jdbcType = JdbcType.VARCHAR),
            @Result(property = "title",column = "title",jdbcType = JdbcType.VARCHAR),
            @Result(property = "gender",column = "gender",jdbcType = JdbcType.INTEGER),
            @Result(property = "age",column = "age",jdbcType = JdbcType.INTEGER),
            @Result(property = "nationality",column = "nationality",jdbcType = JdbcType.VARCHAR),
            @Result(property = "level",column = "level",jdbcType = JdbcType.INTEGER),
            @Result(property = "introduce",column = "introduce",jdbcType = JdbcType.VARCHAR)
    })

    @SelectProvider(type = CoachSqlProvider.class,method = "getAllCoachesBriefs")
    List<CoachInfo> getAllCoachesBriefs();

    @ResultMap("coachRM")
    @SelectProvider(type = CoachSqlProvider.class,method = "getCoachById")
    Coach getCoachById(@Param("coachId") int id) ;

    class CoachSqlProvider{
        static List<String> simpleCols = Arrays.asList("name","heading_img_url","title","nationality","level");
        static List<String> allCols = Arrays.asList("id","name","age","heading_img_url","title","gender","nationality","level","introduce");
        public String getAllCoachesBriefs(){
            return new SQL(){
                {
                    SELECT(simpleCols.toArray(new String[simpleCols.size()])+"count(t1.id) as calsses");
                    FROM(COACHTABLE+" t");
                    LEFT_OUTER_JOIN(COURSECLASSTABLE+" t1 on t.id = t1.coachid");
                    GROUP_BY(simpleCols.toArray(new String[simpleCols.size()]));
                    ORDER_BY("t.level desc");
                }
            }.toString();
        }
        public String getCoachById(final Map<String,Object> param){
            return new SQL(){
                {
                    SELECT(allCols.toArray(new String[allCols.size()]));
                    FROM(COACHTABLE);
                    WHERE("id = #{coachId}");
                    ORDER_BY("level desc");
                }
            }.toString();
        }
    }


}

    /*REATE TABLE IF NOT EXISTS `coach`(
        `id` INT NOT NULL AUTO_INCREMENT,
        `name` VARCHAR(32) NOT NULL COMMENT 'the name of teacher',
        `heading_img_url` VARCHAR(255) DEFAULT NULL,
        `title` VARCHAR(36) DEFAULT NULL,
        `gender` CHAR(1) NOT NULL DEFAULT 'U' COMMENT 'U for unknown, F for female, M for male',
        `age` INT(3) NOT NULL DEFAULT '0',
        `nationality` VARCHAR(30) NOT NULL DEFAULT 'CN' COMMENT 'the nationality, CN for China',
        `level` TINYINT(2) NOT NULL DEFAULT '1*/