package com.iku.sports.mini.admin.controller;

import com.iku.sports.mini.admin.entity.CourseClass;
import com.iku.sports.mini.admin.entity.Favorite;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.model.ClassOverview;
import com.iku.sports.mini.admin.model.Response;
import com.iku.sports.mini.admin.request.AddWatchedClassesRequest;
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
    @GetMapping("/api/courses/{courseId}/classes")
    public Response<List<CourseClass>> paginateClasses(@PathVariable("courseId") short courseId,
            @RequestParam(value = "curPage", defaultValue = "1", required = false) int curPage) throws
            ApiServiceException {
        final List<CourseClass> courseClasses = courseClassService.paginateClasses(courseId, curPage);
        return new Response<List<CourseClass>>().status(Response.SUCCESS).data(courseClasses);
    }

    @ResponseBody
    @PostMapping("/api/classes/{classId}")
    public Response<String> setClassWatchesById(@PathVariable("classId") final int id) throws ApiServiceException {
        courseClassService.incrementWatchesByClassId(id);
        return new Response<String>().status(Response.SUCCESS);
    }

    @ResponseBody
    @GetMapping("/api/classes/{relatedClassId}/promotions")
    public Response<List<CourseClass>> getPromotions(
            @PathVariable("relatedClassId") final int relatedClassId) throws ApiServiceException {
        final List<CourseClass> promotions = courseClassService.getPromotionsById(relatedClassId);
        return new Response<List<CourseClass>>().data(promotions);
    }

    @ResponseBody
    @GetMapping("/api/favorite/classes")
    public Response<List<CourseClass>> getFavoriteClasses(
            @RequestParam("token") final String userId) throws
            ApiServiceException {
        final List<CourseClass> courseClasses = courseClassService
                .getClassesByUserIdAndFavoriteType(userId, Favorite.FavoriteType.FOR_CLASS.getCode());

        return new Response<List<CourseClass>>().status(Response.SUCCESS).data(courseClasses);
    }

    @ResponseBody
    @GetMapping("/api/coaches/{coachId}/classes")
    public Response<List<CourseClass>> getClassesByCoachId(@PathVariable("coachId") final int coachId) throws
            ApiServiceException {
        final List<CourseClass> courseClasses = courseClassService.getClassesByCoachId(coachId);
        return new Response<List<CourseClass>>().status(Response.SUCCESS).data(courseClasses);
    }

    @ResponseBody
    @GetMapping("/api/watched/classes")
    public Response<List<CourseClass>> getWatchedClassesByUserId(@RequestParam("token") final String userId) throws
            ApiServiceException {
        final List<CourseClass> courseClasses = courseClassService.getClassesByUserId(userId);
        return new Response<List<CourseClass>>().status(Response.SUCCESS).data(courseClasses);
    }

    @ResponseBody
    @PostMapping("/api/watched/class")
    public Response<String> addWatchedClassesByUserId(@RequestBody AddWatchedClassesRequest request) throws
            ApiServiceException {
        if (!courseClassService.existsWatchedHis(request.getUserId(), request.getClassId())) {
            courseClassService.saveWatchedClasses(request.getUserId(), request.getClassId());
        }

        return new Response<String>().status(Response.SUCCESS);
    }
}
