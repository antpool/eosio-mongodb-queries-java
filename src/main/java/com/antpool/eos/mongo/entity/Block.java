package com.antpool.eos.mongo.entity;

import com.antpool.eos.mongo.entity.sub.BlockObject;
import lombok.Data;

/**
 * @Author: jiaoqsh
 * @Date: 2018/8/2
 */
@Data
public class Block extends BaseEntity {
    private String blockId;
    private BlockObject block;
    private Long blockNum;
    private Boolean irreversible;
    private Boolean inCurrentChain;
    private Boolean validated;
}