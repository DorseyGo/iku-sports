package com.iku.sports.mini.admin.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品实体
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Goods implements Serializable {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品价格，单位：分
     */
    private Integer price;
    /**
     * 商品划线价格，单位：分
     */
    private Integer markingPrice;
    /**
     * 商品库存
     */
    private Integer stock;
    /**
     * 商品图标
     */
    private String smallLogo;
    private String bigLogo;
    /**
     * 商品状态 {@see GoodsStatusEnum}
     */
    private Integer status;
    /**
     * 商品描述
     */
    private String desc;

    private Date createdAt;
    private Date updatedAt;
}
