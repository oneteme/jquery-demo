```java	
// v0 is the DatasetResource that contains the JoinClause
default JoinsClause myLeftJoin() {
    var v1 = getInstance().getStore(myStore.class).myView();
	return JoinsClause.joins(ViewJoin.leftJoin(v1.getView(), myColumn().eq(v1.id())));
}
```

```javascript
select=...&join=myLeftJoin
```