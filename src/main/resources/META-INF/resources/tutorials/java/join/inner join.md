```java	
// v0 is the DatasetCatalogue that contains the JoinClause
default JoinsClause myInnerJoin() {
    var v1 = getInstance().getStore(myStore.class).myView();
	return JoinsClause.joins(ViewJoin.innerJoin(v1.getView(), myColumn().eq(v1.id())));
}
```

```javascript
select=...&join=myInnerJoin
```