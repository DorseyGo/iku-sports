package com.iku.sports.mini.admin.service;

import com.iku.sports.mini.admin.entity.Collect;

import java.util.List;

/**
 * File: Coach
 * Author: Huanghz
 * Created: 2020/4/18
 * Description:
 * CopyRight: All rights reserved
 **/
public interface CollectService {
    void addCollect(int collectId, int collectType, int studentId) throws Exception;

    void delCollect(int id) throws Exception;

    List<Collect> getCollectByStudentId(int studentId,int collectType) throws Exception;
}
