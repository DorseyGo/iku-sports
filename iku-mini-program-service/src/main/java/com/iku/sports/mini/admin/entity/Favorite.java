package com.iku.sports.mini.admin.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

/**
 * File: Coach
 * Author: Huanghz
 * Created: 2020/4/18
 * Description:
 * CopyRight: All rights reserved
 **/
@Data
@Builder
public class Favorite {

    private int id;
    private String userId;
    private int favoriteId;
    private int favoriteType;

    @Tolerate
    public Favorite() {};

    public enum FavoriteType {
        FOR_ARTICLE(1), FOR_CLASS(2), FOR_COACH(3);
        private final int code;
        FavoriteType(final int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}