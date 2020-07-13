package com.iku.sports.mini.admin.repository;

import com.iku.sports.mini.admin.entity.Appointment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author henlf
 */
@Repository
public interface CourseAppointRepository {

    @Results({
            @Result(property = "id", column = "id", jdbcType = JdbcType.INTEGER),
            @Result(property = "arrangedClassId", column = "arrange_id", jdbcType = JdbcType.INTEGER),
            @Result(property = "userId", column = "user_id", jdbcType = JdbcType.VARCHAR),
            @Result(property = "status", column = "status", jdbcType = JdbcType.INTEGER),
            @Result(property = "updateTime", column = "update_time", jdbcType = JdbcType.DATE),
            @Result(property = "createTime", column = "create_time", jdbcType = JdbcType.DATE)
    })
    @SelectProvider(type = SQLProvider.class, method = "findAppointedClass")
    Appointment findAppointedClass(@Param("userId") String userId, @Param("arrangedClassId") Integer arrangedClassId);

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
    }
}
