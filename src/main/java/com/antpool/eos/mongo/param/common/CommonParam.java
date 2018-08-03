package com.antpool.eos.mongo.param.common;

import com.google.common.collect.Lists;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import lombok.Data;
import org.bson.conversions.Bson;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Author: jiaoqsh
 * @Date: 2018/8/3
 */
@Data
public class CommonParam {
    protected Map<String, Object> match;
    protected Integer limit;
    protected Integer skip;
    protected SortParam sort;

    public CommonParam(Map<String, Object> match, Integer limit, Integer skip, SortParam sort) {
        this.match = match;
        this.limit = limit;
        this.skip = skip;
        this.sort = sort;
    }

    public Optional<Bson> match() {
        if (match == null || match.isEmpty()) {
            return Optional.empty();
        }
        List<Bson> filterList = Lists.newArrayList();
        for (Map.Entry<String, Object> entry : match.entrySet()) {
            filterList.add(Filters.eq(entry.getKey(), entry.getValue()));
        }
        return Optional.of(Aggregates.match(Filters.and(filterList)));
    }

    public Optional<Bson> limit() {
        if (limit == null || limit <= 0) {
            limit = 25;
        }
        return Optional.of(Aggregates.limit(limit));
    }

    public Optional<Bson> skip() {
        if (skip == null || skip <= 0) {
            return Optional.empty();
        }
        return Optional.of(Aggregates.skip(skip));
    }

    public Optional<Bson> sort() {
        if (sort == null) {
            return Optional.empty();
        }
        return Optional.of(Aggregates.sort(Filters.eq(sort.getFieldName(), sort.getSortDirection().getValue())));
    }
}
