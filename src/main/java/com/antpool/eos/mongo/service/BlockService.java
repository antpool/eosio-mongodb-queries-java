package com.antpool.eos.mongo.service;

import com.antpool.eos.mongo.entity.Block;
import com.antpool.eos.mongo.param.BlockParam;
import com.mongodb.client.AggregateIterable;

/**
 * @Author: jiaoqsh
 * @Date: 2018/8/2
 */
public interface BlockService {

    AggregateIterable<Block> getBlocks(BlockParam param);
}
