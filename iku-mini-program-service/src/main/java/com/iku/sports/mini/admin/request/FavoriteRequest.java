package com.iku.sports.mini.admin.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * File: ${FILE_NAME}
 * Author: Huanghz
 * Created: 2020/4/19
 * Description:
 * CopyRight: All rights reserved
 **/
@Data
@Builder
public class FavoriteRequest {
    @Min(1)
    private int favoriteId;
    @Min(1)
    private int favoriteType;
    @NotEmpty
    private String token;

    @Tolerate
    public FavoriteRequest() {
        // empty
    }
}
