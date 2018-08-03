package com.antpool.eos.mongo.service;

import com.antpool.eos.mongo.service.util.MongoResource;
import com.mongodb.client.MongoClient;
import org.junit.After;
import org.junit.Before;

/**
 * @Author: jiaoqsh
 * @Date: 2018/8/3
 */
public class TestBase {

    protected MongoClient mongoClient;

    @Before
    public void setUp() throws Exception {
        mongoClient = MongoResource.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        mongoClient.close();
    }
}
