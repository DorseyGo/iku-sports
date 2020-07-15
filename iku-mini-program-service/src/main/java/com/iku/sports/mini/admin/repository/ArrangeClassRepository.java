package com.iku.sports.mini.admin.repository;

import com.iku.sports.mini.admin.entity.ArrangeClass;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * class arrange
 */
@Repository
public interface ArrangeClassRepository {

    @Results({
            @Result(property = "id", column = "id", jdbcType = JdbcType.INTEGER),
            @Result(property = "classId", column = "class_id", jdbcType = JdbcType.INTEGER),
            @Result(property = "coachId", column = "coach_id", jdbcType = JdbcType.INTEGER),
            @Result(property = "courseId", column = "course_id", jdbcType = JdbcType.TINYINT),
            @Result(property = "site", column = "site", jdbcType = JdbcType.VARCHAR),
            @Result(property = "beginTime", column = "begin_time"),
            @Result(property = "endTime", column = "end_time"),
            @Result(property = "duration", column = "duration", jdbcType = JdbcType.INTEGER),
            @Result(property = "headcount", column = "headcount", jdbcType = JdbcType.INTEGER),
            @Result(property = "appointCount", column = "ordercount", jdbcType = JdbcType.INTEGER),
            @Result(property = "createdTime", column = "create_time"),
            @Result(property = "chapter", column = "chapter", jdbcType = JdbcType.TINYINT),
            @Result(property = "content", column = "content", jdbcType = JdbcType.VARCHAR),
            @Result(property = "name", column = "name", jdbcType = JdbcType.VARCHAR),
    })
    @SelectProvider(type = SQLProvider.class, method = "findArrangeClassByCourseId")
    List<ArrangeClass> findArrangeClassByCourseId(@Param("courseId") short courseId, @Param("aheadTime") Date aheadTime);

    @UpdateProvider(type = SQLProvider.class, method = "updateAppointedCount")
    void updateAppointedCount(@Param("arrangeClassId") Integer arrangeClassId, @Param("updateValue") Integer updateValue);

    class SQLProvider {
        final String TABLE = "arrange_class ac";
        final String TABLE_CLASS = "class c";
        final String TABLE_COACH = "coach co";
        final List<String> COLUMN = Lists.newArrayList("ac.id", "ac.class_id", "ac.coach_id", "ac.course_id",
                "ac.site", "ac.begin_time", "ac.end_time", "ac.duration", "ac.headcount", "ac.ordercount", "ac.create_time");


        public String findArrangeClassByCourseId(final Map<String, Object> params) {
            List<String> classColumn = Lists.newArrayList("c.chapter", "c.content");
            List<String> coachColumn = Lists.newArrayList("co.name");
            COLUMN.addAll(classColumn);
            COLUMN.addAll(coachColumn);

            return new SQL(){
                {
                    SELECT(COLUMN.toArray(new String[0]));
                    FROM(TABLE);
                    LEFT_OUTER_JOIN(TABLE_CLASS + " ON ac.class_id = c.id");
                    LEFT_OUTER_JOIN(TABLE_COACH + " ON ac.coach_id = co.id");
                    WHERE("ac.course_id = #{courseId} and begin_time > #{aheadTime}");
                }
            }.toString();
        }

        public String updateAppointedCount(final Map<String, Object> params) {
            return "update arrange_class set ordercount = ordercount + #{updateValue} where id = #{arrangeClassId}";
        }
    }
}
