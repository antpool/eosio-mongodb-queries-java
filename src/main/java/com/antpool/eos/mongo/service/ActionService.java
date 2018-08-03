package com.antpool.eos.mongo.service;

import com.antpool.eos.mongo.entity.Action;
import com.antpool.eos.mongo.param.ActionParam;
import com.mongodb.client.AggregateIterable;

/**
 * @Author: jiaoqsh
 * @Date: 2018/8/2
 */
public interface ActionService {

    AggregateIterable<Action> getActions(ActionParam param);
}
