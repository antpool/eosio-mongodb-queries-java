package com.antpool.eos.mongo.param.common;

import lombok.Getter;

/**
 * @Author: jiaoqsh
 * @Date: 2018/8/3
 */
public enum SortDirection {
    ASC(1), DESC(-1);

    @Getter
    private int value;

    SortDirection(int value) {
        this.value = value;
    }
}
