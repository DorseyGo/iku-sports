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
        static List<String> simpleCols = Arrays.asList("t.name","t.heading_img_url","t.title","t.nationality","t.level");
        static List<String> allCols = Arrays.asList("t.id","t.name","t.age","t.heading_img_url","t.title","t.gender","t.nationality","t.level","t.introduce");
        public String getAllCoachesBriefs(){
            return new SQL(){
                {
                    SELECT(simpleCols.toArray(new String[simpleCols.size()])+"count(t1.id) as classes");
                    FROM(COACHTABLE+" t");
                    LEFT_OUTER_JOIN(COURSECLASSTABLE+" t1 on t.id = t1.coach_id");
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
