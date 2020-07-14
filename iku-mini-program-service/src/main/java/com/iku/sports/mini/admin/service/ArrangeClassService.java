package com.iku.sports.mini.admin.service;

import com.iku.sports.mini.admin.entity.ArrangeClass;

import java.util.List;

/**
 * @author tanghongfeng
 */
public interface ArrangeClassService {
    /**
     * returns the class arranged specified by {@code courseId}
     * @param courseId id of course
     * @return
     */
    List<ArrangeClass> courseArrange(short courseId, String userId);

    /**
     * 预约人数增加
     * @param arrangeClassId
     */
    void addAppointedCount(Integer arrangeClassId);
}
