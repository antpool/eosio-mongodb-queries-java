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
public class AccountParam extends CommonParam {
    private String name;
    private Boolean irreversible;
    private Long lteBlockNum;

    @Builder
    public AccountParam(Map<String, Object> match, Integer limit, Integer skip, SortParam sort, String name, Boolean irreversible, Long lteBlockNum) {
        super(match, limit, skip, sort);
        this.name = name;
        this.irreversible = irreversible;
        this.lteBlockNum = lteBlockNum;
    }
}
