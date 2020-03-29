package com.iku.sports.mini.admin.repository;

import com.iku.sports.mini.admin.entity.Klass;
import com.sun.org.apache.bcel.internal.generic.Select;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * File: KlassRepository
 * Author: Huanghz
 * Created: 2020/3/28
 * Description:
 * CopyRight: All rights reserved
 **/
@Repository("klassRepository")
public interface KlassRepository {
    String TABLE = "class";

    @Results(id = "classRM",value = {
            @Result(property = "id", column = "id", jdbcType = JdbcType.INTEGER),
            @Result(property = "title", column = "title", jdbcType = JdbcType.VARCHAR),
            @Result(property = "chapter", column = "chapter", jdbcType = JdbcType.TINYINT),
            @Result(property = "videoUrl", column = "video_url", jdbcType = JdbcType.VARCHAR),
            @Result(property = "content", column = "content", jdbcType = JdbcType.VARCHAR),
            @Result(property = "watches", column = "watches", jdbcType = JdbcType.BIGINT),
            @Result(property = "courseId", column = "course_id", jdbcType = JdbcType.TINYINT),
            @Result(property = "teacherId", column = "teacher_id", jdbcType = JdbcType.INTEGER)
    })

    @SelectProvider(type = KlassSqlProvider.class,method = "getAllClass")
    List<Klass> getAllClass();

    class KlassSqlProvider{
        static  final List<String> COLS = Arrays.asList("id","title","chapter","video_url","content","watches","course_id","teacher_id");
        public String getAllClass(){
            return new SQL(){
                {
                    SELECT(COLS.toArray(new String[COLS.size()]));
                    FROM(TABLE);
                }
            }.toString();
        }
    }


}
