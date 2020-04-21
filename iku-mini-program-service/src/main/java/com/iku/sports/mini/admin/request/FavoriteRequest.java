package com.iku.sports.mini.admin.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

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


    private int favoriteId;
    private int favoriteType;
    private String userId;

    @Tolerate
    public FavoriteRequest() {
        // empty
    }
}
