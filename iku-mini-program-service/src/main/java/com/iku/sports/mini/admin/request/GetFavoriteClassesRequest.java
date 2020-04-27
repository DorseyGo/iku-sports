/*
 * File: GetFavoriteClassesRequest
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
public class GetFavoriteClassesRequest {

    @JsonProperty("token")
    private String userId;

    @JsonProperty("favoriteType")
    private int favoriteType;

    @Tolerate
    public GetFavoriteClassesRequest() {
        // empty
    }
}
