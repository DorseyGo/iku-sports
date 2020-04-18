package com.iku.sports.mini.admin.repository;

import com.iku.sports.mini.admin.entity.Collect;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * File: CollectRepository
 * Author: Huanghz
 * Created: 2020/4/18
 * Description:
 * CopyRight: All rights reserved
 **/
@Repository("collectRepository")
public interface CollectRepository {

    @InsertProvider(type = CollectSQLProvider.class, method = "addCollect")
    void addCollect(@Param("collectId") int collectId,
                           @Param("collectType") int collectType,
                           @Param("studentId") int studentId) throws Exception;

    @DeleteProvider(type = CollectSQLProvider.class, method = "delCollect")
    void delCollect(@Param("id") int id) throws Exception;

    @Results(id= "collectRM",value = {
            @Result(property = "id",column = "id",jdbcType = JdbcType.INTEGER),
            @Result(property = "studentId",column = "student_id",jdbcType = JdbcType.INTEGER),
            @Result(property = "collectId",column = "collect_id",jdbcType = JdbcType.INTEGER),
            @Result(property = "collectType",column = "collect_type",jdbcType = JdbcType.INTEGER)
    })
    @SelectProvider(type = CollectSQLProvider.class, method = "getCollectByStudentId")
    List<Collect> getCollectByStudentId(@Param("studentId") int studentId);

    @ResultMap("collectRM")
    @SelectProvider(type = CollectSQLProvider.class, method = "getCollectByStudentIdCollectType")
    List<Collect> getCollectByStudentIdCollectType(@Param("studentId") int studentId,@Param("collectType") int collectType);

    @ResultMap("collectRM")
    @SelectProvider(type = CollectSQLProvider.class, method = "getCollectSummaryByStudentId")
    Integer getCollectSummaryByStudentId(@Param("studentId") int studentId);

    //SQL provider
    class CollectSQLProvider {
        static String TABLE = "collect";

        static final List<String> ALL_COLS = Arrays.asList("id","student_id","collect_id","collect_type");

        public String getCollectSummaryByStudentId(final Map<String, Object> param){
            return new SQL(){
                {
                    SELECT("IFNULL(count(1),0)");
                    FROM(TABLE);
                    WHERE("student_id = #{studentId}");
                }
            }.toString();
        }

        public String getCollectByStudentIdCollectType(final Map<String, Object> param){
            return new SQL(){
                {
                    SELECT(ALL_COLS.toArray(new String[ALL_COLS.size()]));
                    FROM(TABLE);
                    WHERE("student_id = #{studentId}");
                    WHERE("collect_type = #{collectType}");
                }
            }.toString();
        }

        public String getCollectByStudentId(final Map<String, Object> param){
            return new SQL(){
                {
                    SELECT(ALL_COLS.toArray(new String[ALL_COLS.size()]));
                    FROM(TABLE);
                    WHERE("student_id = #{studentId}");

                }
            }.toString();
        }

        public String addCollect(final Map<String, Object> params){

            return new SQL(){
                {
                    INSERT_INTO(TABLE);
                    VALUES("student_id","#{studentId}");
                    VALUES("collect_id","#{collectId}");
                    VALUES("student_id","#{studentId}");
                    VALUES("collect_type","#{collectType}");
                }
            }.toString();

        }

        public String delCollect(final Map<String, Object> params){
            return new SQL(){
                {
                    DELETE_FROM(TABLE);
                    WHERE("id = #{id}");
                }
            }.toString();
        }
    }

}
