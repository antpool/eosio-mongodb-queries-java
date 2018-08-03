package com.antpool.eos.mongo.service.impl;

import com.antpool.eos.mongo.entity.Block;
import com.antpool.eos.mongo.param.BlockParam;
import com.antpool.eos.mongo.service.AbstractService;
import com.antpool.eos.mongo.service.BlockService;
import com.google.common.collect.Lists;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.jongo.Mapper;

import java.util.List;


/**
 * @Author: jiaoqsh
 * @Date: 2018/8/2
 */
public class BlockServiceImpl extends AbstractService implements BlockService {

    public BlockServiceImpl(MongoClient mongoClient) {
        super(mongoClient);
    }

    public BlockServiceImpl(MongoClient mongoClient, Mapper mapper) {
        super(mongoClient, mapper);
    }

    @Override
    protected String collectionName() {
        return "blocks";
    }

    @Override
    protected Class documentClass() {
        return Block.class;
    }

    @Override
    public AggregateIterable<Block> getBlocks(BlockParam param) {
        // MongoDB Pipeline
        List<Bson> pipeline = Lists.newArrayList();

        // Match by data entries
        // options.match //=> {"data.from": "eosio"}
        push(param.match(), pipeline);

        // Filter by Reference Block Number
        push(match("block_id", param.getBlockId()), pipeline);
        push(match("block_num", param.getBlockNum()), pipeline);
        // Both greater & lesser Block Number
        if (!isNullOrInvalidity(param.getLteBlockNum()) && !isNullOrInvalidity(param.getGetBlockNum())) {
            pipeline.add(Aggregates.match(Filters.and(
                    Filters.lte("block_num", param.getLteBlockNum()),
                    Filters.gte("block_num", param.getGetBlockNum())
            )));
        } else {
            if (!isNullOrInvalidity(param.getLteBlockNum())) {
                pipeline.add(Aggregates.match(Filters.lte("block_num", param.getLteBlockNum())));
            }
            if (!isNullOrInvalidity(param.getGetBlockNum())) {
                pipeline.add(Aggregates.match(Filters.gte("block_num", param.getGetBlockNum())));
            }
        }

        // Sort by ascending or descending
        push(param.sort(), pipeline);
        // Support Pagination using Skip & Limit
        push(param.skip(), pipeline);
        push(param.limit(), pipeline);

        return collection.aggregate(pipeline);
    }
}
