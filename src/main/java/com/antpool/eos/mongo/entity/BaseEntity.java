package com.antpool.eos.mongo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.jongo.marshall.jackson.oid.MongoObjectId;

/**
 * @Author: jiaoqsh
 * @Date: 2018/8/2
 */
@Data
public class BaseEntity {

    @MongoObjectId
    @JsonProperty("_id")
    protected String _id;

    @JsonProperty("createdAt")
    protected String createdAt;

    @JsonProperty("updatedAt")
    protected String updatedAt;
}
