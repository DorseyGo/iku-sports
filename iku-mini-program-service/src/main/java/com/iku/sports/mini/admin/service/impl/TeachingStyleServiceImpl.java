/*
 * File: TeachingStyleServiceImpl
 * Author: DorSey Q F TANG
 * Created: 2020/7/16
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.iku.sports.mini.admin.config.IkuSportsConfig;
import com.iku.sports.mini.admin.entity.TeachingStyle;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.model.Constants;
import com.iku.sports.mini.admin.model.SingleHolder;
import com.iku.sports.mini.admin.repository.TeachingStyleRepository;
import com.iku.sports.mini.admin.service.TeachingStyleService;
import com.iku.sports.mini.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@Service("teachingStyleService")
public class TeachingStyleServiceImpl implements TeachingStyleService {

    private final TeachingStyleRepository teachingStyleRepository;
    private final IkuSportsConfig config;

    @Autowired
    public TeachingStyleServiceImpl(
            @Qualifier("teachingStyleRepository") final TeachingStyleRepository teachingStyleRepository,
            IkuSportsConfig config) {
        this.teachingStyleRepository = teachingStyleRepository;
        this.config = config;
    }

    @Override
    public List<TeachingStyle> getTeachingStyles() throws ApiServiceException {
        final List<TeachingStyle> teachingStyles = teachingStyleRepository.findAll();
        teachingStyles.forEach(teachingStyle -> postProcess(teachingStyle));

        return teachingStyles;
    }

    @Override
    public TeachingStyle getTeachingStyleById(int styleId) throws ApiServiceException {
        final TeachingStyle teachingStyle = teachingStyleRepository.findTeachingStyleById(styleId);
        postProcess(teachingStyle);

        return teachingStyle;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public void updateWatchesById(int styleId, long watches) throws ApiServiceException, DataAccessException {
        teachingStyleRepository.updateWatchesById(styleId, watches);
    }

    private void postProcess(TeachingStyle teachingStyle) {
        if (!Strings.isNullOrEmpty(teachingStyle.getCover())) {
            teachingStyle.setCover(Utils.join(config.getStaticResourceServer(), teachingStyle.getCover(),
                    Constants.FORWARD_SLASH));
        }

        if (!Strings.isNullOrEmpty(teachingStyle.getVideo())) {
            teachingStyle.setVideo(
                    Utils.join(config.getStaticResourceServer(), teachingStyle.getVideo(), Constants.FORWARD_SLASH));
        }

        if (!Strings.isNullOrEmpty(teachingStyle.getTags())) {
            final List<String> tags = Splitter.on(Constants.DELIM_COMMA).splitToList(teachingStyle.getTags());
            final List<SingleHolder> labels = Lists.newArrayListWithExpectedSize(2);
            tags.forEach(tag -> labels.add(SingleHolder.builder().name(tag).build()));

            teachingStyle.setLabels(labels.toArray(new SingleHolder[labels.size()]));
        }
    }
}
