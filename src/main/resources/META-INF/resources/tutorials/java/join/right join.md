```java	
// v0 is the DatasetCatalogue that contains the JoinGroup
default JoinGroup myRightJoin() {
    var v1 = getInstance().getStore(myStore.class).myView();
	return JoinGroup.joins(Join.rightJoin(v1.getView(), myColumn().eq(v1.id())));
}
```

```javascript
select=...&join=myRightJoin
```