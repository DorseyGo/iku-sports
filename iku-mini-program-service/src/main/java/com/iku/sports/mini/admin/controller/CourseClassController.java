package com.iku.sports.mini.admin.controller;

import com.iku.sports.mini.admin.entity.Course;
import com.iku.sports.mini.admin.entity.CourseClass;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.model.ClassCount;
import com.iku.sports.mini.admin.model.ClassOverview;
import com.iku.sports.mini.admin.model.Response;
import com.iku.sports.mini.admin.request.GetFavoriteClassesRequest;
import com.iku.sports.mini.admin.service.CourseClassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * File: ClassController
 * Author: Huanghz
 * Created: 2020/3/28
 * Description:class management
 * CopyRight: All rights reserved
 **/
@Slf4j
@Controller
public class CourseClassController {

    private final CourseClassService courseClassService;

    @Autowired
    public CourseClassController(@Qualifier("courseClassService") final CourseClassService courseClassService) {
        this.courseClassService = courseClassService;
    }

    @ResponseBody
    @GetMapping("/api/classes/{id}")
    public Response<ClassOverview> getClassById(@PathVariable("id") final int id) throws ApiServiceException {
        final ClassOverview overview = courseClassService.getClassOverviewById(id);
        return new Response<ClassOverview>().status(Response.SUCCESS)
                .data(overview);
    }

    @ResponseBody
    @GetMapping("/api/classes/first3/{courseId}")
    public Response<List<CourseClass>> getFirst3ClassesByCourseId(@PathVariable("courseId") final short courseId) {
        try {
            final List<CourseClass> courseClass = courseClassService.getFirst3ClassesByCourseId(courseId);
            return new Response<List<CourseClass>>().status(Response.SUCCESS)
                    .data(courseClass);
        } catch (Exception e) {
            log.error("Fail to get class by courseid.", e);
            return new Response<List<CourseClass>>().status(Response.FAIL);
        }
    }

    @ResponseBody
    @GetMapping("/api/courses/{courseId}/classes")
    public Response<List<CourseClass>> getClassesByCourseId(@PathVariable("courseId") final short courseId) {
        try {
            final List<CourseClass> courseClass = courseClassService.getClassesByCourseId(courseId);
            return new Response<List<CourseClass>>().status(Response.SUCCESS)
                    .data(courseClass);
        } catch (Exception e) {
            log.error("Fail to get class by courseid.", e);
            return new Response<List<CourseClass>>().status(Response.FAIL);
        }
    }

    @ResponseBody
    @GetMapping("/api/classes")
    public Response<List<CourseClass>> paginateClasses(@RequestParam("courseId") short courseId,
            @RequestParam("offset") int offset,
            @RequestParam("pageSize") int pageSize) {
        try {
            final List<CourseClass> courseClass = courseClassService.paginateClasses(courseId, offset, pageSize);
            return new Response<List<CourseClass>>().status(Response.SUCCESS)
                    .data(courseClass);
        } catch (Exception e) {
            log.error("Fail to get paginateclasses.", e);
            return new Response<List<CourseClass>>().status(Response.FAIL);
        }
    }

    @ResponseBody
    @GetMapping("/api/classes/popular/category/{categoryId}")
    public Response<List<CourseClass>> getTop3PopularClasses(@PathVariable("categoryId") short categoryId) {
        try {
            final List<CourseClass> courseClasses = courseClassService.getTop3PopularClasses(categoryId);
            return new Response<List<CourseClass>>().status(Response.SUCCESS)
                    .data(courseClasses);
        } catch (Exception e) {
            log.error("Fail to get Popularclasses.", e);
            return new Response<List<CourseClass>>().status(Response.FAIL);
        }
    }

    @ResponseBody
    @GetMapping("/api/classes/top3/category")
    public Response<List<CourseClass>> getTop3ClassicByCategoryId(@RequestParam("categoryId") final short categoryId,
            @RequestParam("days") final int days) {
        try {
            final List<CourseClass> courseClasses = courseClassService.getTop3ClassicByCategoryId(categoryId, days);
            return new Response<List<CourseClass>>().status(Response.SUCCESS)
                    .data(courseClasses);
        } catch (Exception e) {
            log.error("Fail to get top3 class by CategoryId.", e);
            return new Response<List<CourseClass>>().status(Response.FAIL);
        }
    }

    @ResponseBody
    @GetMapping("/api/classes/count/{courseId}")
    public Response<ClassCount> getTotalNumMoneyByCourseId(@PathVariable("courseId") final int courseId) {
        try {
            final ClassCount classCount = courseClassService.getTotalNumMoneyByCourseId(courseId);
            return new Response<ClassCount>().status(Response.SUCCESS)
                    .data(classCount);
        } catch (Exception e) {
            log.error("Fail to get quantity and amount.", e);
            return new Response<ClassCount>().status(Response.FAIL);
        }
    }

    @ResponseBody
    @GetMapping("/api/class/updateWatches/{id}")
    public Response setClassWatchesById(@PathVariable("id") final int id) {
        try {
            courseClassService.setClassWatchesById(id);
            return new Response().status(Response.SUCCESS);
        } catch (Exception e) {
            log.error("Fail to set watches of calss by id:{}", id, e);
            return new Response().status(Response.FAIL);
        }
    }

    @ResponseBody
    @GetMapping("/api/classes/{relatedClassId}/promotions")
    public Response<List<CourseClass>> getPromotions(
            @PathVariable("relatedClassId") final int relatedClassId) throws ApiServiceException {
        final List<CourseClass> promotions = courseClassService.getPromotionsById(relatedClassId);
        return new Response<List<CourseClass>>().data(promotions);
    }

    @ResponseBody
    @PostMapping("/api/favorite/classes")
    public Response<List<CourseClass>> getFavoriteClasses(@RequestBody GetFavoriteClassesRequest request) throws
            ApiServiceException {
        final List<CourseClass> courseClasses = courseClassService
                .getClassesByUserIdAndFavoriteType(request.getUserId(), request.getFavoriteType());

        return new Response<List<CourseClass>>().status(Response.SUCCESS).data(courseClasses);
    }
}
