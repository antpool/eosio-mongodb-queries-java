package com.antpool.eos.mongo.param.common;

import lombok.Data;

/**
 * @Author: jiaoqsh
 * @Date: 2018/8/3
 */
@Data
public class SortParam {
    private String fieldName;
    private SortDirection sortDirection;

    public SortParam(String fieldName, SortDirection sortDirection) {
        this.fieldName = fieldName;
        this.sortDirection = sortDirection;
    }
}