package com.antpool.eos.mongo.param;

import com.antpool.eos.mongo.param.common.CommonParam;
import com.antpool.eos.mongo.param.common.SortParam;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author: jiaoqsh
 * @Date: 2018/8/2
 */
@Data
public class BlockParam extends CommonParam {
    private Long blockNum;
    private String blockId;
    private Long lteBlockNum;
    private Long getBlockNum;

    @Builder
    public BlockParam(Map<String, Object> match, Integer limit, Integer skip, SortParam sort, Long blockNum, String blockId, Long lteBlockNum, Long getBlockNum) {
        super(match, limit, skip, sort);
        this.blockNum = blockNum;
        this.blockId = blockId;
        this.lteBlockNum = lteBlockNum;
        this.getBlockNum = getBlockNum;
    }
}
