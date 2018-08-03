package com.antpool.eos.mongo.service;

import com.antpool.eos.mongo.entity.Action;
import com.antpool.eos.mongo.param.ActionParam;
import com.antpool.eos.mongo.param.common.SortDirection;
import com.antpool.eos.mongo.param.common.SortParam;
import com.antpool.eos.mongo.service.impl.ActionServiceImpl;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mongodb.client.AggregateIterable;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @Author: jiaoqsh
 * @Date: 2018/8/2
 */
public class ActionServiceTest extends TestBase {

    private ActionService service;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        service = new ActionServiceImpl(mongoClient);
    }

    @Test
    public void getOnBlockActions() {
        ActionParam param = ActionParam.builder()
                .accounts(Lists.newArrayList("eosio"))
                .names(Lists.newArrayList("onblock"))
                .limit(5)
                .build();
        AggregateIterable<Action> actions = service.getActions(param);
        assertThat(actions.iterator().hasNext()).isTrue();
        for (Action action : actions) {
            System.out.println(action);
            assertThat(action.getAccount().equals("eosio"));
        }
    }

    @Test
    public void getBuyRamBytes() {
        ActionParam param = ActionParam.builder()
                .accounts(Lists.newArrayList("eosio"))
                .names(Lists.newArrayList("buyrambytes"))
                .limit(5)
                .skip(5)
                .sort(new SortParam("block_num", SortDirection.DESC))
                .build();
        AggregateIterable<Action> actions = service.getActions(param);
        assertThat(actions.iterator().hasNext()).isTrue();
        for (Action action : actions) {
            System.out.println(action);
            assertThat(action.getAccount().equals("eosio"));
        }
    }

    @Test
    public void getDelegateBWActions() {
        ActionParam param = ActionParam.builder()
                .accounts(Lists.newArrayList("eosio"))
                .names(Lists.newArrayList("delegatebw", "undelegatebw"))
                .match(ImmutableMap.of("data.from", "eosnationftw", "data.receiver", "eosnationftw"))
                .irreversible(true)
                .sort(new SortParam("block_num", SortDirection.DESC))
                .build();
        AggregateIterable<Action> actions = service.getActions(param);
        assertThat(actions.iterator().hasNext()).isTrue();
        for (Action action : actions) {
            System.out.println(action);
            assertThat(action.getAccount().equals("eosio"));
        }
    }
}