package com.antpool.eos.mongo.service;

import com.antpool.eos.mongo.entity.Block;
import com.antpool.eos.mongo.param.BlockParam;
import com.antpool.eos.mongo.param.common.SortDirection;
import com.antpool.eos.mongo.param.common.SortParam;
import com.antpool.eos.mongo.service.impl.BlockServiceImpl;
import com.google.common.collect.ImmutableMap;
import com.mongodb.client.AggregateIterable;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @Author: jiaoqsh
 * @Date: 2018/8/3
 */
public class BlockServiceTest extends TestBase {

    private BlockService service;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        service = new BlockServiceImpl(mongoClient);
    }

    @Test
    public void getBlocksByBlockNum() {
        BlockParam param = BlockParam.builder()
                .blockNum(2L)
                .limit(1)
                .build();
        AggregateIterable<Block> actions = service.getBlocks(param);
        assertThat(actions.iterator().hasNext()).isTrue();
        for (Block block : actions) {
            System.out.println(block);
            assertThat(block.getBlockNum().equals(2L));
        }
    }

    @Test
    public void getBlocks() {
        BlockParam param = BlockParam.builder()
                .match(ImmutableMap.of("block.producer", "eoscanadacom"))
                .sort(new SortParam("block_num", SortDirection.ASC))
                .limit(5)
                .skip(5)
                .build();
        AggregateIterable<Block> actions = service.getBlocks(param);
        assertThat(actions.iterator().hasNext()).isTrue();
        for (Block block : actions) {
            System.out.println(block);
            assertThat(block.getBlock().getProducer().equals("eoscanadacom"));
        }
    }
}