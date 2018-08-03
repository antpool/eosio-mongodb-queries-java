package com.antpool.eos.mongo.service.impl;

import com.antpool.eos.mongo.entity.Action;
import com.antpool.eos.mongo.param.ActionParam;
import com.antpool.eos.mongo.service.AbstractService;
import com.antpool.eos.mongo.service.ActionService;
import com.google.common.collect.Lists;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.jongo.Aggregate;
import org.jongo.Mapper;

import java.util.Arrays;
import java.util.List;


/**
 * @Author: jiaoqsh
 * @Date: 2018/8/2
 */
public class ActionServiceImpl extends AbstractService implements ActionService {

    public ActionServiceImpl(MongoClient mongoClient) {
        super(mongoClient);
    }

    public ActionServiceImpl(MongoClient mongoClient, Mapper mapper) {
        super(mongoClient, mapper);
    }

    @Override
    protected String collectionName() {
        return "actions";
    }

    @Override
    protected Class documentClass() {
        return Action.class;
    }

    @Override
    public AggregateIterable<Action> getActions(ActionParam param) {
        // MongoDB Pipeline
        List<Bson> pipeline = Lists.newArrayList();
        // Filter by Transaction ID
        push(match("trx_id", param.getTxId()), pipeline);
        // Filter account contracts
        // eg: ["eosio", "eosio.token"]
        push(matchOr("account", param.getAccounts()), pipeline);
        // Filter action names
        // eg: ["delegatebw", "undelegatebw"]
        push(matchOr("name", param.getNames()), pipeline);

        // Match by data entries
        // options.match //=> {"data.from": "eosio"}
        push(param.match(), pipeline);

        // Get Reference Block Number from Transaction Id
        pipeline.add(Aggregates.graphLookup("transactions", "$trx_id", "trx_id", "trx_id", "transactions"));

        withProject(pipeline, param);

        // Filter by Reference Block Number
        push(match("irreversible", param.getIrreversible()), pipeline);
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


    protected void withProject(List pipeline, ActionParam param) {
        Bson project = Aggregates.project(Projections.fields(
                Projections.include("_id", "action_num", "trx_id", "cfa", "account", "name", "authorization", "data"),
                Projections.computed("irreversible", getArrayElemAtExpression("$transactions.irreversible")),
                Projections.computed("transaction_header", getArrayElemAtExpression("$transactions.transaction_header")),
                Projections.computed("signing_keys", getArrayElemAtExpression("$transactions.signing_keys")),
                Projections.computed("signatures", getArrayElemAtExpression("$transactions.signatures")),
                Projections.computed("block_id", getArrayElemAtExpression("$transactions.block_id")),
                Projections.computed("block_num", getArrayElemAtExpression("$transactions.block_num"))
        ));

        pipeline.add(project);
    }

    private Document getArrayElemAtExpression(String expression) {
        return new Document("$arrayElemAt", Arrays.asList(expression, 0));
    }

}
