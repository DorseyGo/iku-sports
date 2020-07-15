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
     * 更新预约人数
     * @param arrangeClassId
     * @param updateValue 正数：添加，负数：减少
     */
    void updateAppointedCount(Integer arrangeClassId, Integer updateValue);
}
