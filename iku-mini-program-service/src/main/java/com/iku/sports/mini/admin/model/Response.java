/**
 * File: Response
 * Author: DorSey Q F TANG
 * Created: 2020/3/19
 * CopyRight: All rights reserved
 **/
package com.iku.sports.mini.admin.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Tolerate;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class Response<T> implements Serializable {
    private int statusCode;
    private String errorPhase;
    private T data;

    public static final int SUCCESS = 0;
    public static final int FAIL = 1;

    public Response<T> status(final int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public Response<T> data(final T data) {
        this.data = data;
        return this;
    }
}
