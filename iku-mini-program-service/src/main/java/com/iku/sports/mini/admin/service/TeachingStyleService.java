/*
 * File: TeachingStyleService
 * Author: DorSey Q F TANG
 * Created: 2020/7/16
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service;

import com.iku.sports.mini.admin.entity.TeachingStyle;
import com.iku.sports.mini.admin.exception.ApiServiceException;

import java.util.List;

public interface TeachingStyleService {

    /**
     * Returns all available teaching styles in the underlying database.
     *
     * @return all available teaching styles.
     * @throws ApiServiceException if any errors detected during process.
     */
    List<TeachingStyle> getTeachingStyles() throws ApiServiceException;

    /**
     * Returns the teaching style according to the given style ID.
     *
     * @param styleId the style ID.
     * @return the teaching style.
     * @throws ApiServiceException if any errors detected during process.
     */
    TeachingStyle getTeachingStyleById(int styleId) throws ApiServiceException;

    /**
     * Update the watches by the style ID.
     *
     * @param styleId the style ID.
     * @param watches the watches.
     * @throws ApiServiceException if any errors detected during process.
     */
    void updateWatchesById(int styleId, long watches) throws ApiServiceException;
}
