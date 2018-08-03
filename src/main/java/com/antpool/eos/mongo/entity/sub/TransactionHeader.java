package com.antpool.eos.mongo.entity.sub;

import lombok.Data;

/**
 * @Author: jiaoqsh
 * @Date: 2018/8/2
 */
@Data
public class TransactionHeader {
    private String expiration;
    private Long refBlockNum;
    private String refBlockPrefix;
    private Long maxNetUsageWords;
    private Long maxCpuUsageMs;
    private Long delaySec;
}
