```java	
// v0 is the DatasetResource that contains the JoinClause
default JoinsClause myLeftJoin() {
    var v1 = getInstance().getStore(myStore.class).myView();
	return JoinsClause.joins(ViewJoin.leftJoin(v1.getView(), column().eq(v1.id())));
}
```

```javascript
select=...&join=myLeftJoin
```