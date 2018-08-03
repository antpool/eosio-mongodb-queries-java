package com.antpool.eos.mongo.entity;

import com.antpool.eos.mongo.entity.sub.Authorization;
import com.antpool.eos.mongo.entity.sub.TransactionHeader;
import lombok.Data;

import java.util.List;

/**
 * @Author: jiaoqsh
 * @Date: 2018/8/2
 */
@Data
public class Action extends BaseEntity {
    private Long actionNum;
    private String trxId;
    private Boolean cfa;
    private String account;
    private String name;
    private List<Authorization> authorization;
    private Object data;
    private Boolean irreversible;
    private TransactionHeader transactionHeader;
    private Object signingKeys;
    private Object signatures;
    private Long blockNum;
    private String blockId;
    private String hexData;
}
