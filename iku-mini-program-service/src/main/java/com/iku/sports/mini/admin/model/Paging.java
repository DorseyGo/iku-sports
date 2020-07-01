package com.iku.sports.mini.admin.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页
 */
@Getter
@NoArgsConstructor
public class Paging<T> implements Serializable {
    /**
     * 页面大小
     */
    private int pageSize;
    /**
     * 偏移量
     */
    private int offset;
    /**
     * 页码
     */
    private int pageNum;
    /**
     * 总数据量
     */
    private int total;
    /**
     * 数据
     */
    private List<T> data;
    /**
     * 是否还有下一页
     */
    private boolean hasNextPaging = false;

    public Paging(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.offset = (this.pageNum - 1) * 10;
    }

    public Paging<T> setTotal(int total) {
        this.total = total;
        int totalPage = total % pageSize == 0 ? total % pageSize : (total % pageSize) + 1;
        if (pageNum <= totalPage) {
            hasNextPaging = true;
        }

        return this;
    }

    public Paging<T> setData(List<T> data) {
        this.data = data;
        return this;
    }
}
