package com.antpool.eos.mongo.entity;

import com.antpool.eos.mongo.entity.sub.ABI;
import lombok.Data;

/**
 * @Author: jiaoqsh
 * @Date: 2018/8/2
 */
@Data
public class Account extends BaseEntity {
    private String name;
    private ABI abi;
    private String updatedAt;
}
