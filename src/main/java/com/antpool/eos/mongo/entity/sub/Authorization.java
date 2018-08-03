package com.antpool.eos.mongo.entity.sub;

import lombok.Data;

/**
 * @Author: jiaoqsh
 * @Date: 2018/8/2
 */
@Data
public class Authorization {
    private String actor;
    private String permission;
}
