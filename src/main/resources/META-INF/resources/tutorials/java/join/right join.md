```java	
// v0 is the DatasetCatalogue that contains the JoinClause
default JoinsClause myRightJoin() {
    var v1 = getInstance().getStore(myStore.class).myView();
	return JoinsClause.joins(ViewJoin.rightJoin(v1.getView(), myColumn().eq(v1.id())));
}
```

```javascript
select=...&join=myRightJoin
```