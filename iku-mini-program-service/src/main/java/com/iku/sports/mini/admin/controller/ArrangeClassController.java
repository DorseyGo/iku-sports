package com.iku.sports.mini.admin.controller;

import com.iku.sports.mini.admin.annotation.WebLog;
import com.iku.sports.mini.admin.entity.ArrangeClass;
import com.iku.sports.mini.admin.model.Response;
import com.iku.sports.mini.admin.service.ArrangeClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author henlf
 */
@RestController
public class ArrangeClassController {
    private ArrangeClassService arrangeClassService;

    @Autowired
    public ArrangeClassController(ArrangeClassService arrangeClassService) {
        this.arrangeClassService = arrangeClassService;
    }

    @WebLog(response = false)
    @GetMapping("arrange/class/{courseId}")
    public Response<List<ArrangeClass>> arrangedClass(@NotNull(message = "courseId cannot be null")
                                                    @PathVariable("courseId") Short courseId,
                                                      @NotNull @RequestParam("userId") String userId) {
        List<ArrangeClass> arrangeClasses = arrangeClassService.courseArrange(courseId, userId);
        return Response.ok(arrangeClasses);
    }
}
