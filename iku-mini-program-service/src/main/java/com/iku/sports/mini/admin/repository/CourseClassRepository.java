package com.iku.sports.mini.admin.repository;

import com.iku.sports.mini.admin.entity.CourseClass;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * File: CourseClassRepository
 * Author: Huanghz
 * Created: 2020/3/28
 * Description:
 * CopyRight: All rights reserved
 **/
@Repository("courseClassRepository")
public interface CourseClassRepository {
    String CLASSTABLE = "class";
    String COURSETABLE = "course";

    @Results(id = "courseClassRM",value = {
            @Result(property = "id", column = "id", jdbcType = JdbcType.INTEGER),
            @Result(property = "title", column = "title", jdbcType = JdbcType.VARCHAR),
            @Result(property = "chapter", column = "chapter", jdbcType = JdbcType.TINYINT),
            @Result(property = "videoUrl", column = "video_url", jdbcType = JdbcType.VARCHAR),
            @Result(property = "content", column = "content", jdbcType = JdbcType.VARCHAR),
            @Result(property = "watches", column = "watches", jdbcType = JdbcType.BIGINT),
            @Result(property = "courseId", column = "course_id", jdbcType = JdbcType.TINYINT),
            @Result(property = "teacherId", column = "teacher_id", jdbcType = JdbcType.INTEGER)
    })

    @ResultMap("courseClassRM")
    @SelectProvider(type = CourseClassSqlProvider.class,method = "getFirst3ClassesByCourseId")
    List<CourseClass> getFirst3ClassesByCourseId(@Param("first3ClassCourseId") short courseId) throws Exception;

    @ResultMap("courseClassRM")
    @SelectProvider(type = CourseClassSqlProvider.class,method = "paginateClasses")
    List<CourseClass> paginateClasses(@Param("paginateCourseId") short courseId,
                                      @Param("paginateOffset") int offset,
                                      @Param("paginatePageSize") int pageSize)throws Exception;
    @ResultMap("courseClassRM")
    @SelectProvider(type = CourseClassSqlProvider.class,method = "getClassById")
    CourseClass getClassById(@Param("calssId") int id)throws Exception;

    @ResultMap("courseClassRM")
    @SelectProvider(type = CourseClassSqlProvider.class,method = "getTop3PopularClasses")
    List<CourseClass> getTop3PopularClasses(@Param("popularId") short categoryId)throws Exception;

    @ResultMap("courseClassRM")
    @SelectProvider(type = CourseClassSqlProvider.class,method = "getTop3ClassicByCategoryId")
    List<CourseClass> getTop3ClassicByCategoryId(@Param("top3CategoryId") short categoryId,
                                                 @Param("top3CategoryDays") int days)throws Exception;

    class CourseClassSqlProvider {
        static final List<String> ALLCOLS = Arrays.asList("id","title","cover","chapter","video_url","content","watches","course_id","teacher_id");
        static final List<String> SIMPLECOLS = Arrays.asList("t.id","t.title","t.cover","t.content","t.watches","t.course_id");

        public String getTop3ClassicByCategoryId(final Map<String,Object> params){
            return new SQL(){
                {
                    SELECT(SIMPLECOLS.toArray(new String[SIMPLECOLS.size()]));
                    FROM(CLASSTABLE+" T");
                    LEFT_OUTER_JOIN(COURSETABLE+" T2 ON T.course_id = T2.id");
                    if(params.get("top3CategoryId") != null){
                        WHERE("T2.category_id=#{top3CategoryId}");
                    }

                    if(params.get("top3CategoryDays") != null) {
                        WHERE("t.create_time < date_substr(sysdate(),interval #{days} day)");
                    }
                }
            }.toString();
        }

        public String getTop3PopularClasses(final Map<String,Object> params){
            return new SQL(){
                {
                    SELECT(SIMPLECOLS.toArray(new String[SIMPLECOLS.size()]));
                    FROM(CLASSTABLE+" T");
                    LEFT_OUTER_JOIN(COURSETABLE+" T2 ON T.course_id = T2.id");
                    if (params.get("popularId") != null){
                        WHERE("T2.category_id=#{popularId}");
                    }
                    if ((params.get("paginateOffset") != null) && (params.get("paginatePageSize")) != null ){
                        ORDER_BY("T.create_time desc,T.watches desc limit #{paginateOffset},#{paginatePageSize}");
                    }
                }
            }.toString();
        }
        public String getClassById(final Map<String,Object> param){
            return new SQL(){
                {
                    SELECT(ALLCOLS.toArray(new String[ALLCOLS.size()]));
                    FROM(CLASSTABLE+" T");
                    if (param.get("classId") != null){
                        WHERE(" T.ID = #{classId}");
                    }
                    ORDER_BY("T.create_time desc,T.watches desc");
                }
            }.toString();
        }

        public String paginateClasses(final Map<String,Object> params) {
            return new SQL(){
                {
                    SELECT(SIMPLECOLS.toArray(new String[SIMPLECOLS.size()]));
                    FROM(CLASSTABLE+" T");
                    if (params.get("paginateCourseId") != null){
                        WHERE("course_id=#{paginateCourseId}");
                    }
                    if ((params.get("paginateOffset") != null) && (params.get("paginatePageSize")) != null ){
                        ORDER_BY("T.create_time desc,T.watches desc limit #{paginateOffset},#{paginatePageSize}");
                    }
                }
            }.toString();
        }

        public String getFirst3ClassesByCourseId(final Map<String,Object> param){
            return new SQL(){
                {
                    SELECT(SIMPLECOLS.toArray(new String[SIMPLECOLS.size()]));
                    FROM(CLASSTABLE+" T");
                    LEFT_OUTER_JOIN(COURSETABLE+" T2 ON T.course_id = T2.ID");
                    if (param.get("first3ClassCourseId") != null) {
                        WHERE("T2.ID = #{first3ClassCourseId}");
                    }
                    ORDER_BY("T.course_id DESC LIMIT 0,3");


                }
            }.toString();
        }
    }



}
