/*
 * File: PaymentService
 * Author: DorSey Q F TANG
 * Created: 2020/4/22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service;

import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.model.Prepayment;
import com.iku.sports.mini.admin.request.PaymentRequest;

public interface PaymentService {

    /**
     * Prepay the given course.
     *
     * @param userId identifies who will pay the course.
     * @param courseId identifies the course
     * @return the prepayment, which contains the required information for later payment.
     * @throws ApiServiceException if any errors detected during process.
     */
    Prepayment prepayCourse(final String userId, final short courseId) throws ApiServiceException;
}
