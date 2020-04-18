package com.iku.sports.mini.admin.service.impl;

import com.iku.sports.mini.admin.entity.Collect;
import com.iku.sports.mini.admin.repository.CollectRepository;
import com.iku.sports.mini.admin.service.CollectService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * File: Coach
 * Author: Huanghz
 * Created: 2020/4/18
 * Description:
 * CopyRight: All rights reserved
 **/
@Service("collectService")
public class CollectServiceImpl implements CollectService {

    private final CollectRepository collectRepository;

    private Integer totalNum;

    public CollectServiceImpl(@Qualifier("collectRepository") CollectRepository collectRepository) {
        this.collectRepository = collectRepository;
    }

    @Override
    public void addCollect(int collectId, int collectType, int studentId) throws Exception {
        collectRepository.addCollect(collectId,collectType,studentId);
    }

    @Override
    public void delCollect(int id) throws Exception {
        collectRepository.delCollect(id);
    }

    @Override
    public List<Collect> getCollectByStudentId(int studentId,int collectType) throws Exception {
        if(collectType == 0 ){
            return collectRepository.getCollectByStudentId(studentId);
        }
        return collectRepository.getCollectByStudentIdCollectType(studentId,collectType);
    }

    @Override
    public Integer getCollectSummaryByStudentId(int studentId) throws Exception {
        totalNum = collectRepository.getCollectSummaryByStudentId(studentId);
        if(totalNum == null ){
            return 0;
        }else{
            return totalNum;
        }
    }
}
