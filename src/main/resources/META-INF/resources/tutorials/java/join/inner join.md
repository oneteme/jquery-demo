```java	
// v0 is the DatasetCatalogue that contains the JoinGroup
default JoinGroup myInnerJoin() {
    var v1 = getInstance().getStore(myStore.class).myView();
	return JoinGroup.joins(Join.innerJoin(v1.getView(), myColumn().eq(v1.id())));
}
```

```javascript
select=...&join=myInnerJoin
```