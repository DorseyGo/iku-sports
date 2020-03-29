package com.iku.sports.mini.admin.service.impl;

import com.iku.sports.mini.admin.entity.Klass;
import com.iku.sports.mini.admin.repository.KlassRepository;
import com.iku.sports.mini.admin.service.KlassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * File: KlassServiceImpl
 * Author: Huanghz
 * Created: 2020/3/28
 * Description:Implements KlassService
 * CopyRight: All rights reserved
 **/
@Service("klassService")
public class KlassServiceImpl implements KlassService {
    private final KlassRepository klassRepository ;

    @Autowired
    public KlassServiceImpl(@Qualifier("klassRepository") KlassRepository klassRepository) {
        this.klassRepository = klassRepository;
    }

    @Override
    public List<Klass> getAllClass() {
        return klassRepository.getAllClass();
    }
}
