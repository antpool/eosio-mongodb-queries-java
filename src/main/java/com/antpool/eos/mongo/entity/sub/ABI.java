package com.antpool.eos.mongo.entity.sub;

import lombok.Data;

/**
 * @Author: jiaoqsh
 * @Date: 2018/8/2
 */
@Data
public class ABI {
    private String version;
    private TypeElement types;
    private Struct[] structs;
    private Action[] actions;
    private Table[] tables;
    private Object[] ricardianClauses;
    private Object[] errorMessages;
    private Object[] abiExtensions;
}

@Data
class TypeElement {
    private String newTypeName;
    private String type;
}

@Data
class Field {
    private String name;
    private String type;
}

@Data
class Struct {
    private String name;
    private String base;
    private Field[] fields;
}

@Data
class Action {
    private String name;
    private String base;
    private String ricardianContract;
}

@Data
class Table {
    private String name;
    private String indexType;
    private String[] keyNames;
    private String[] keyTypes;
    private String type;
}

