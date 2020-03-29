package com.iku.sports.mini.admin.service.impl;

import com.iku.sports.mini.admin.entity.CourseClass;
import com.iku.sports.mini.admin.repository.CourseClassRepository;
import com.iku.sports.mini.admin.service.CourseClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * File: CourseClassServiceImpl
 * Author: Huanghz
 * Created: 2020/3/28
 * Description:Implements CourseClassService
 * CopyRight: All rights reserved
 **/
@Service("courseClassService")
public class CourseClassServiceImpl implements CourseClassService {
    private final CourseClassRepository courseClassRepository;

    @Autowired
    public CourseClassServiceImpl(@Qualifier("courseClassRepository") CourseClassRepository courseClassRepository) {
        this.courseClassRepository = courseClassRepository;
    }

    @Override
    public List<CourseClass> getAllClasses() {
        return courseClassRepository.getAllClass();
    }

    @Override
    public List<CourseClass> getTop3ClassesByCourseId(short courseId) {
        return null;
    }

    @Override
    public List<CourseClass> paginateClasses(short courseId, int offset, int pageSize) {
        return null;
    }

    @Override
    public CourseClass getClassById(int id) {
        return null;
    }
}
