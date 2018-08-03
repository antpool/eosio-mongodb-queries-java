package com.antpool.eos.mongo.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.jongo.Jongo;
import org.jongo.JongoNative;
import org.jongo.Mapper;
import org.jongo.marshall.jackson.JacksonMapper;
import org.jongo.marshall.jackson.bson4jackson.BsonModule;
import org.jongo.marshall.jackson.bson4jackson.MongoBsonFactory;
import org.jongo.marshall.jackson.configuration.AnnotationModifier;
import org.jongo.marshall.jackson.configuration.PropertyModifier;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: jiaoqsh
 * @Date: 2018/8/3
 */
public abstract class AbstractService<TDocument> {
    protected static final String DATABASE_NAME = "EOS";

    private final MongoDatabase database;
    protected MongoCollection<TDocument> collection;

    public AbstractService(MongoClient mongoClient) {
        this(mongoClient, getDefaultMapper());
    }

    public AbstractService(MongoClient mongoClient, Mapper mapper) {
        this.database = mongoClient.getDatabase(DATABASE_NAME);
        JongoNative jongo = Jongo.useNative(mapper);
        this.collection = jongo.wrap(database.getCollection(collectionName(), documentClass()));
    }

    protected static Mapper getDefaultMapper() {
        ObjectMapper mapper = new ObjectMapper(MongoBsonFactory.createFactory());
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return new JacksonMapper.Builder(mapper)
                .registerModule(new BsonModule())
                .addModifier(new PropertyModifier())
                .addModifier(new AnnotationModifier())
                .build();
    }

    protected abstract String collectionName();

    protected abstract Class<TDocument> documentClass();

    protected void push(Optional<Bson> bsonOptional, List<Bson> pipeline) {
        bsonOptional.ifPresent(bson -> pipeline.add(bson));
    }

    protected Optional<Bson> matchOr(String fileName, List<Object> params) {
        if (params == null || params.isEmpty()) {
            return Optional.empty();
        }
        List<Bson> filterList = params.stream().map(param -> Filters.eq(fileName, param)).collect(Collectors.toList());
        return Optional.ofNullable(Aggregates.match(Filters.or(filterList)));
    }

    protected Optional<Bson> match(String fileName, Object param) {
        if (isNullOrInvalidity(param)) {
            return Optional.empty();
        }
        return Optional.ofNullable(Aggregates.match(Filters.eq(fileName, param)));
    }

    protected boolean isNullOrInvalidity(Object param) {
        if (param == null) {
            return true;
        }
        if (param instanceof String && ((String) param).length() == 0) {
            return true;
        }
        if (param instanceof Long && (Long) param <= 0) {
            return true;
        }
        return false;
    }
}
