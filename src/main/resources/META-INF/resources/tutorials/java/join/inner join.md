```java	
// v0 is the DatasetResource that contains the JoinClause
default JoinsClause myInnerJoin() {
    var v1 = getInstance().getStore(myStore.class).myView();
	return joins(innerJoin(v1.getView(), column().eq(v1.id())));
}
```

```javascript
select=...&join=myInnerJoin
```