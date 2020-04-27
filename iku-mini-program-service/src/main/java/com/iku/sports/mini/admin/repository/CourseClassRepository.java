package com.iku.sports.mini.admin.repository;

import com.google.common.collect.Lists;
import com.iku.sports.mini.admin.entity.CourseClass;
import com.iku.sports.mini.admin.model.ClassCount;
import com.iku.sports.mini.admin.model.ClassOverview;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

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
    String TABLE_COACH = "coach";
    String TABLE_FAVORITE = "favorite";

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
    @SelectProvider(type = CourseClassSqlProvider.class, method = "getFirst3ClassesByCourseId")
    List<CourseClass> getFirst3ClassesByCourseId(@Param("first3ClassCourseId") short courseId) throws Exception;

    @ResultMap("courseClassRM")
    @SelectProvider(type = CourseClassSqlProvider.class, method = "paginateClasses")
    List<CourseClass> paginateClasses(@Param("paginateCourseId") short courseId,
            @Param("paginateOffset") int offset,
            @Param("paginatePageSize") int pageSize) throws Exception;

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
    @SelectProvider(type = CourseClassSqlProvider.class, method = "getClassOverviewById")
    ClassOverview getClassOverviewById(@Param("classId") int id) throws DataAccessException;

    @ResultMap("courseClassRM")
    @SelectProvider(type = CourseClassSqlProvider.class, method = "getTop3PopularClasses")
    List<CourseClass> getTop3PopularClasses(@Param("popularId") short categoryId) throws Exception;

    @ResultMap("courseClassRM")
    @SelectProvider(type = CourseClassSqlProvider.class, method = "getTop3ClassicByCategoryId")
    List<CourseClass> getTop3ClassicByCategoryId(@Param("top3CategoryId") short categoryId,
            @Param("top3CategoryDays") int days) throws Exception;

    @ResultMap("courseClassRM")
    @SelectProvider(type = CourseClassSqlProvider.class, method = "getClassByCourseId")
    List<CourseClass> getClassByCourseId(@Param("courseId") short courseId);

    @Results(id = "courseClassCountRM", value = {
            @Result(property = "totalCnt", column = "totalCnt", jdbcType = JdbcType.INTEGER),
            @Result(property = "chapter", column = "chapter", jdbcType = JdbcType.DOUBLE)
    })
    @SelectProvider(type = CourseClassSqlProvider.class, method = "getTotalNumMoneyByCourseId")
    ClassCount getTotalNumMoneyByCourseId(@Param("courseId") int courseId);

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
                    UPDATE(CLASSTABLE);
                    SET("watches = watches + 1");
                    WHERE("id = #{classId}");
                }
            }.toString();
        }

        public String getTotalNumMoneyByCourseId(final Map<String, Object> param) {
            return new SQL() {
                {
                    SELECT("count(1) totalCnt,sum(chapter) chapter");
                    FROM(CLASSTABLE);
                    WHERE("course_id = #{courseId}");
                }
            }.toString();
        }

        public String getClassByCourseId(final Map<String, Object> param) {
            return new SQL() {
                {
                    SELECT(ALLCOLS.toArray(new String[ALLCOLS.size()]));
                    FROM(CLASSTABLE);
                    WHERE("course_id = #{courseId}");
                    ORDER_BY("watches desc");
                }
            }.toString();
        }

        public String getTop3ClassicByCategoryId(final Map<String, Object> params) {
            return new SQL() {
                {
                    SELECT(SIMPLECOLS.toArray(new String[SIMPLECOLS.size()]));
                    FROM(CLASSTABLE + " T");
                    LEFT_OUTER_JOIN(COURSETABLE + " T2 ON T.course_id = T2.id");
                    if (params.get("top3CategoryId") != null) {
                        WHERE("T2.category_id=#{top3CategoryId}");
                    }

                    if (params.get("top3CategoryDays") != null) {
                        WHERE("t.create_time < date_substr(sysdate(),interval #{days} day)");
                    }
                }
            }.toString();
        }

        public String getTop3PopularClasses(final Map<String, Object> params) {
            return new SQL() {
                {
                    SELECT(SIMPLECOLS.toArray(new String[SIMPLECOLS.size()]));
                    FROM(CLASSTABLE + " T");
                    LEFT_OUTER_JOIN(COURSETABLE + " T2 ON T.course_id = T2.id");
                    if (params.get("popularId") != null) {
                        WHERE("T2.category_id=#{popularId}");
                    }
                    if ((params.get("paginateOffset") != null) && (params.get("paginatePageSize")) != null) {
                        ORDER_BY("T.create_time desc,T.watches desc limit #{paginateOffset},#{paginatePageSize}");
                    }
                }
            }.toString();
        }

        public String getClassOverviewById(final Map<String, Object> param) {
            return new SQL() {
                {
                    SELECT(COLS_CLASS.toArray(new String[COLS_CLASS.size()]));
                    FROM(CLASSTABLE + " c");
                    INNER_JOIN(TABLE_COACH + " ch ON ch.id = c.coach_id");
                    WHERE("c.id = #{classId}");
                }
            }.toString();
        }

        public String paginateClasses(final Map<String, Object> params) {
            return new SQL() {
                {
                    SELECT(SIMPLECOLS.toArray(new String[SIMPLECOLS.size()]));
                    FROM(CLASSTABLE + " T");
                    if (params.get("paginateCourseId") != null) {
                        WHERE("course_id=#{paginateCourseId}");
                    }
                    if ((params.get("paginateOffset") != null) && (params.get("paginatePageSize")) != null) {
                        ORDER_BY("T.create_time desc,T.watches desc limit #{paginateOffset},#{paginatePageSize}");
                    }
                }
            }.toString();
        }

        public String getFirst3ClassesByCourseId(final Map<String, Object> param) {
            return new SQL() {
                {
                    SELECT(SIMPLECOLS.toArray(new String[SIMPLECOLS.size()]));
                    FROM(CLASSTABLE + " T");
                    LEFT_OUTER_JOIN(COURSETABLE + " T2 ON T.course_id = T2.ID");
                    if (param.get("first3ClassCourseId") != null) {
                        WHERE("T2.ID = #{first3ClassCourseId}");
                    }
                    ORDER_BY("T.course_id DESC LIMIT 0,3");


                }
            }.toString();
        }

        public String findFirst2ByClassId(final Map<String, Object> params) {
            return new SQL() {
                {
                    SELECT(SIMPLE_COLS_CLASS.toArray(new String[SIMPLE_COLS_CLASS.size()]));
                    FROM(CLASSTABLE + " c");
                    INNER_JOIN(CLASSTABLE + " c1 ON c.course_id = c1.course_id");
                    WHERE("c1.id = #{relatedClassId} AND c.id != #{relatedClassId}");
                    ORDER_BY("c.watches DESC LIMIT 2");
                }
            }.toString();
        }

        public String findClassesByUserIdAndFavoriteType(final Map<String, Object> params) {
            return new SQL() {
                {
                    SELECT(SIMPLE_COLS_CLASS.toArray(new String[SIMPLE_COLS_CLASS.size()]));
                    FROM(CLASSTABLE + " c");
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
                    FROM(CLASSTABLE + " c");
                    WHERE("c.coach_id = #{coachId}");
                }
            }.toString();
        }
    }


}
