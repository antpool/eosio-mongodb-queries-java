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
public class ActionParam extends CommonParam {
    private List<String> accounts;
    private List<String> names;
    private Boolean irreversible;
    private String txId;
    private Long blockNum;
    private String blockId;
    private Long lteBlockNum;
    private Long getBlockNum;

    @Builder
    public ActionParam(Map<String, Object> match, Integer limit, Integer skip, SortParam sort, List<String> accounts, List<String> names, Boolean irreversible, String txId, Long blockNum, String blockId, Long lteBlockNum, Long getBlockNum) {
        super(match, limit, skip, sort);
        this.accounts = accounts;
        this.names = names;
        this.irreversible = irreversible;
        this.txId = txId;
        this.blockNum = blockNum;
        this.blockId = blockId;
        this.lteBlockNum = lteBlockNum;
        this.getBlockNum = getBlockNum;
    }
}
