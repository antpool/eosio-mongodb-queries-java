package com.antpool.eos.mongo.entity.sub;

import lombok.Data;

import java.util.List;

/**
 * @Author: jiaoqsh
 * @Date: 2018/8/3
 */
@Data
public class BlockObject {
    private String timestamp;
    private String producer;
    private Long confirmed;
    private String previous;
    private String transactionMroot;
    private String actionMroot;
    private Long scheduleVersion;
    private Object newProducers;
    private List<Object> headerExtensions;
    private String producerSignature;
    private List<Object> transactions;
    private List<Object> blockExtensions;
}

