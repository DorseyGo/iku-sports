package com.iku.sports.mini.admin.repository;

import com.google.common.collect.Lists;
import com.iku.sports.mini.admin.entity.CourseClass;
import com.iku.sports.mini.admin.model.ClassOverview;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;
import org.springframework.dao.DataAccessException;
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
    String TABLE = "class";
    String TABLE_COACH = "coach";
    String TABLE_FAVORITE = "favorite";
    String TABLE_CLASSES_WATCHED_HIS = "class_watched_his";

    @Results(id = "courseClassRM", value = {
            @Result(property = "id", column = "id", jdbcType = JdbcType.INTEGER),
            @Result(property = "title", column = "title", jdbcType = JdbcType.VARCHAR),
            @Result(property = "chapter", column = "chapter", jdbcType = JdbcType.TINYINT),
            @Result(property = "videoUrl", column = "video_url", jdbcType = JdbcType.VARCHAR),
            @Result(property = "content", column = "content", jdbcType = JdbcType.VARCHAR),
            @Result(property = "watches", column = "watches", jdbcType = JdbcType.BIGINT),
            @Result(property = "courseId", column = "course_id", jdbcType = JdbcType.TINYINT),
            @Result(property = "coachId", column = "coach_id", jdbcType = JdbcType.INTEGER),
            @Result(property = "cover", column = "cover", jdbcType = JdbcType.VARCHAR)

    })
    @SelectProvider(type = CourseClassSqlProvider.class, method = "paginateClasses")
    List<CourseClass> paginateClasses(@Param("paginateCourseId") short courseId,
            @Param("paginateOffset") int offset,
            @Param("paginatePageSize") int pageSize) throws DataAccessException;

    @Results(id = "coRM", value = {
            @Result(property = "classId", column = "id", jdbcType = JdbcType.INTEGER),
            @Result(property = "nextClassId", column = "next_id", jdbcType = JdbcType.INTEGER),
            @Result(property = "classTitle", column = "title", jdbcType = JdbcType.VARCHAR),
            @Result(property = "nextClassTitle", column = "next_title", jdbcType = JdbcType.VARCHAR),
            @Result(property = "chapter", column = "chapter", jdbcType = JdbcType.INTEGER),
            @Result(property = "nextChapter", column = "next_chapter", jdbcType = JdbcType.INTEGER),
            @Result(property = "videoUrl", column = "video_url", jdbcType = JdbcType.VARCHAR),
            @Result(property = "content", column = "content", jdbcType = JdbcType.VARCHAR),
            @Result(property = "numSeries", column = "num_series", jdbcType = JdbcType.INTEGER),
            @Result(property = "coachName", column = "name", jdbcType = JdbcType.VARCHAR)
    })
    @SelectProvider(type = CourseClassSqlProvider.class, method = "findClassOverviewById")
    ClassOverview findClassOverviewById(@Param("classId") int id) throws DataAccessException;

    @UpdateProvider(type = CourseClassSqlProvider.class, method = "incrementWatchesByClassId")
    void incrementWatchesByClassId(@Param("classId") int id) throws DataAccessException;

    /**
     *
     * @author DorSey
     */
    @Results(id = "simpleClassRM", value = {
            @Result(property = "id", column = "id", jdbcType = JdbcType.INTEGER),
            @Result(property = "title", column = "title", jdbcType = JdbcType.VARCHAR),
            @Result(property = "cover", column = "cover", jdbcType = JdbcType.VARCHAR),
            @Result(property = "content", column = "content", jdbcType = JdbcType.VARCHAR),
            @Result(property = "watches", column = "watches", jdbcType = JdbcType.INTEGER)
    })
    @SelectProvider(type = CourseClassSqlProvider.class, method = "findFirst2ByClassId")
    List<CourseClass> findFirst2ByClassId(@Param("relatedClassId") final int relatedClassId);

    /**
     *
     * @author DorSey
     */
    @ResultMap("simpleClassRM")
    @SelectProvider(type = CourseClassSqlProvider.class, method = "findClassesByUserIdAndFavoriteType")
    List<CourseClass> findClassesByUserIdAndFavoriteType(@Param("userId") String userId,
            @Param("favoriteType") int favoriteType) throws DataAccessException;

    @ResultMap("simpleClassRM")
    @SelectProvider(type = CourseClassSqlProvider.class, method = "findClassesByCoachId")
    List<CourseClass> findClassesByCoachId(@Param("coachId") int coachId) throws DataAccessException;

    @ResultMap("simpleClassRM")
    @SelectProvider(type = CourseClassSqlProvider.class, method = "findWatchedClassesByUserId")
    List<CourseClass> findWatchedClassesByUserId(@Param("userId") String userId) throws DataAccessException;

    @InsertProvider(type = CourseClassSqlProvider.class, method = "saveWatchedClasses")
    void saveWatchedClasses(@Param("userId") String userId, @Param("classId") int classId) throws DataAccessException;

    class CourseClassSqlProvider {
        static final List<String> ALLCOLS = Arrays
                .asList("id", "title", "cover", "chapter", "video_url", "content", "watches", "course_id", "coach_id");
        static final List<String> SIMPLECOLS = Arrays
                .asList("T.id", "T.title", "T.cover", "T.content", "T.watches", "T.course_id");

        static final List<String> COLS_CLASS = Lists
                .newArrayList("c.id", "c.title", "c.chapter", "c.video_url", "c.content");
        static final List<String> COLS_COACH = Lists.newArrayList("ch.name");

        static final List<String> SIMPLE_COLS_CLASS = Lists
                .newArrayList("c.id", "c.title", "c.cover", "c.content", "c.watches");

        static {
            COLS_CLASS.addAll(COLS_COACH);
            COLS_CLASS.add("(SELECT COUNT(c1.id) FROM class c1 WHERE c1.course_id = c.course_id) num_series");
            COLS_CLASS
                    .add("(SELECT c2.id FROM class c2 WHERE c2.chapter > c.chapter ORDER BY c2.chapter ASC LIMIT 0,1)" +
                         " next_id");
            COLS_CLASS
                    .add("(SELECT c3.chapter FROM class c3 WHERE c3.chapter > c.chapter ORDER BY c3.chapter ASC LIMIT" +
                         " 0,1) next_chapter");
            COLS_CLASS
                    .add("(SELECT c4.title FROM class c4 WHERE c4.chapter > c.chapter ORDER BY c4.chapter ASC LIMIT " +
                         "0,1) next_title");
        }

        public String incrementWatchesByClassId(final Map<String, Object> param) {
            return new SQL() {
                {
                    UPDATE(TABLE);
                    SET("watches = watches + 1");
                    WHERE("id = #{classId}");
                }
            }.toString();
        }

        public String findClassOverviewById(final Map<String, Object> param) {
            return new SQL() {
                {
                    SELECT(COLS_CLASS.toArray(new String[COLS_CLASS.size()]));
                    FROM(TABLE + " c");
                    INNER_JOIN(TABLE_COACH + " ch ON ch.id = c.coach_id");
                    WHERE("c.id = #{classId}");
                }
            }.toString();
        }

        public String paginateClasses(final Map<String, Object> params) {
            return new SQL() {
                {
                    SELECT(SIMPLECOLS.toArray(new String[SIMPLECOLS.size()]));
                    FROM(TABLE + " T");
                    if (params.get("paginateCourseId") != null) {
                        WHERE("course_id=#{paginateCourseId}");
                    }
                    if ((params.get("paginateOffset") != null) && (params.get("paginatePageSize")) != null) {
                        ORDER_BY("T.create_time desc,T.watches desc limit #{paginateOffset},#{paginatePageSize}");
                    }
                }
            }.toString();
        }

        public String findFirst2ByClassId(final Map<String, Object> params) {
            return new SQL() {
                {
                    SELECT(SIMPLE_COLS_CLASS.toArray(new String[SIMPLE_COLS_CLASS.size()]));
                    FROM(TABLE + " c");
                    INNER_JOIN(TABLE + " c1 ON c.course_id = c1.course_id");
                    WHERE("c1.id = #{relatedClassId} AND c.id != #{relatedClassId}");
                    ORDER_BY("c.watches DESC LIMIT 2");
                }
            }.toString();
        }

        public String findClassesByUserIdAndFavoriteType(final Map<String, Object> params) {
            return new SQL() {
                {
                    SELECT(SIMPLE_COLS_CLASS.toArray(new String[SIMPLE_COLS_CLASS.size()]));
                    FROM(TABLE + " c");
                    INNER_JOIN(TABLE_FAVORITE + " f ON c.id = f.favorite_id");
                    if (params.get("userId") != null) {
                        WHERE("user_id = #{userId}");
                    }

                    WHERE("favorite_type = #{favoriteType}");
                }
            }.toString();
        }

        public String findClassesByCoachId(final Map<String, Object> params) {
            return new SQL() {
                {
                    SELECT(SIMPLE_COLS_CLASS.toArray(new String[SIMPLE_COLS_CLASS.size()]));
                    FROM(TABLE + " c");
                    WHERE("c.coach_id = #{coachId}");
                }
            }.toString();
        }

        public String findWatchedClassesByUserId(final Map<String, Object> params) {
            return new SQL() {
                {
                    SELECT(SIMPLE_COLS_CLASS.toArray(new String[SIMPLE_COLS_CLASS.size()]));
                    FROM(TABLE + " c");
                    INNER_JOIN(TABLE_CLASSES_WATCHED_HIS + " h ON c.id = h.class_id");
                    if (params.get("userId") != null) {
                        WHERE("h.user_id = #{userId}");
                    }
                }
            }.toString();
        }

        public String saveWatchedClasses(final Map<String, Object> params) {
            return new SQL() {
                {
                    INSERT_INTO(TABLE_CLASSES_WATCHED_HIS);
                    if (params.get("userId") != null) {
                        VALUES("user_id", "#{userId}");
                    }

                    VALUES("class_id", "#{classId}");
                }
            }.toString();
        }
    }


}
