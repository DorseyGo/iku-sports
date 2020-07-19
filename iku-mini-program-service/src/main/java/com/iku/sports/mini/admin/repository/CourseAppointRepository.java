package com.iku.sports.mini.admin.repository;

import com.iku.sports.mini.admin.entity.Appointment;
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
 * @author henlf
 */
@Repository
public interface CourseAppointRepository {

    @Results(id = "appointment", value = {
            @Result(property = "id", column = "id", jdbcType = JdbcType.INTEGER),
            @Result(property = "arrangedClassId", column = "arrange_id", jdbcType = JdbcType.INTEGER),
            @Result(property = "userId", column = "user_id", jdbcType = JdbcType.VARCHAR),
            @Result(property = "status", column = "status", jdbcType = JdbcType.INTEGER),
            @Result(property = "updateTime", column = "update_time", jdbcType = JdbcType.DATE),
            @Result(property = "createTime", column = "create_time", jdbcType = JdbcType.DATE)
    })
    @SelectProvider(type = SQLProvider.class, method = "findAppointedClass")
    Appointment findAppointedClass(@Param("userId") String userId, @Param("arrangedClassId") Integer arrangedClassId);

    @InsertProvider(type = SQLProvider.class, method = "appointment")
    void appointment(@Param("userId") String userId, @Param("arrangeClassId") Integer arrangeClassId);

    @UpdateProvider(type = SQLProvider.class, method = "cancelAppointment")
    void cancelAppointment(@Param("userId") String userId, @Param("arrangeClassId") Integer arrangeClassId);

    @ResultMap("appointment")
    @SelectProvider(type = SQLProvider.class, method = "countUserAppointment")
    List<Appointment> countUserAppointment(@Param("userId") String userId);

    @SelectProvider(type = SQLProvider.class, method = "findUserStudiedClassIds")
    List<Integer> findUserStudiedClassIds(@Param("userId") String userId, @Param("recently") Date recently);

    @SelectProvider(type = SQLProvider.class, method = "findUserStudiedCourseClassByUserIdAndCourseId")
    int findUserStudiedCourseClassByUserIdAndCourseId(@Param("userId") String userId, @Param("courseId") Short courseId);

    @SelectProvider(type = SQLProvider.class, method = "completedClass")
    void completedClass();

    @SelectProvider(type = SQLProvider.class, method = "userAttendClass")
    void userAttendClass(@Param("aheadMinutesConfirmAppoint") Date aheadMinutesConfirmAppoint);

    class SQLProvider {
        final String TABLE = "appointment";
        final List<String> COLUMN = Lists.newArrayList("`id`", "`arrange_id`", "`user_id`",
                "`status`", "`update_time`", "`create_time`");

        public String findAppointedClass(final Map<String, Object> params) {
            return new SQL() {
                {
                    SELECT(COLUMN.toArray(new String[0]));
                    FROM(TABLE);
                    WHERE("user_id = #{userId} and arrange_id = #{arrangedClassId} and status = 1");
                }
            }.toString();
        }

        public String appointment(final Map<String, Object> params) {
            return new SQL() {
                {
                    INSERT_INTO(TABLE);
                    INTO_COLUMNS("arrange_id", "user_id");
                    INTO_VALUES("#{arrangeClassId}", "#{userId}");
                }
            }.toString();
        }

        public String cancelAppointment(final Map<String, Object> params) {
            return "update appointment set status = 0 where user_id = #{userId} and arrange_id = #{arrangeClassId}";
        }

        public String countUserAppointment(final Map<String, Object> params) {
            return new SQL() {
                {
                    SELECT(COLUMN.toArray(new String[0]));
                    FROM(TABLE);
                    WHERE("user_id = #{userId} and status >= 1");
                }
            }.toString();
        }

        public String findUserStudiedClassIds(final Map<String, Object> params) {
            return new SQL() {
                {
                    SELECT("ac.class_id");
                    FROM("appointment a");
                    LEFT_OUTER_JOIN("arrange_class ac on a.arrange_id = ac.id");
                    WHERE("a.user_id = #{userId} and a.status = 3 and a.update_time >= #{recently}");

                }
            }.toString();
        }

        public String findUserStudiedCourseClassByUserIdAndCourseId(final Map<String, Object> params) {
            return new SQL() {
                {
                    SELECT("count(*)");
                    FROM("appointment a");
                    LEFT_OUTER_JOIN("arrange_class ac on a.arrange_id = ac.id");
                    WHERE("a.user_id = #{userId} and a.status = 3 and ac.course_id = #{courseId}");
                }
            }.toString();
        }

        public String completedClass(final Map<String, Object> params) {
            return new SQL() {
                {
                    UPDATE(TABLE);
                    SET("status = 3");
                    WHERE("status = 2");
                }
            }.toString();
        }

        public String userAttendClass(final Map<String, Object> params) {
            return new SQL() {
                {
                    UPDATE(TABLE);
                    SET("status = 2");
                    WHERE("status = 1 and update_time <= #{aheadMinutesConfirmAppoint}");
                }
            }.toString();
        }
    }
}
