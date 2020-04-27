/*
 * File: AddWatchedClassesRequest
 * Author: DorSey Q F TANG
 * Created: 2020/4/27
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class AddWatchedClassesRequest {

    @JsonProperty("token")
    private String userId;

    @JsonProperty("classId")
    private int classId;

    @Tolerate
    public AddWatchedClassesRequest() {}
}
