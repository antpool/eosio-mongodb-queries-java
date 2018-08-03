# EOSIO MongoDB Queries

using Java create MongoDB Queries for the EOSIO blockchain, impl [eosio-mongodb-queries](https://github.com/EOS-BP-Developers/eosio-mongodb-queries) API.

## Quickstart

```java
MongoClient mongoClient = MongoClients.create("mongodb://host1:27017");
BlockService service = new BlockServiceImpl(mongoClient);

BlockParam param = BlockParam.builder()
        .blockNum(2L)
        .limit(1)
        .build();
AggregateIterable<Block> actions = service.getBlocks(param);
```

## API

### [getActions](https://github.com/EOS-BP-Developers/eosio-mongodb-queries#getactions)

#### Examples

```java
ActionParam param = ActionParam.builder()
        .accounts(Lists.newArrayList("eosio"))
        .names(Lists.newArrayList("delegatebw", "undelegatebw"))
        .match(ImmutableMap.of("data.from", "eosnationftw", "data.receiver", "eosnationftw"))
        .irreversible(true)
        .sort(new SortParam("block_num", SortDirection.DESC))
        .build();
AggregateIterable<Action> actions = service.getActions(param);
for (Action action : actions) {
    System.out.println(action);
}
```

Returns **AggregateIterable&lt;Action>** MongoDB Aggregation Iterable

### [getblocks](https://github.com/EOS-BP-Developers/eosio-mongodb-queries#getblocks)

#### Examples

```java
BlockParam param = BlockParam.builder()
        .match(ImmutableMap.of("block.producer", "eoscanadacom"))
        .sort(new SortParam("block_num", SortDirection.ASC))
        .limit(5)
        .skip(5)
        .build();
AggregateIterable<Block> actions = service.getBlocks(param);
for (Block block : actions) {
    System.out.println(block);
}
```

Returns **AggregateIterable&lt;Block>** MongoDB Aggregation Iterable