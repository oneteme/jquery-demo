```java	
// v0 is the DatasetCatalogue that contains the JoinGroup
default JoinGroup myLeftJoin() {
    var v1 = getInstance().getStore(myStore.class).myView();
	return JoinGroup.joins(Join.leftJoin(v1.getView(), myColumn().eq(v1.id())));
}
```

```javascript
select=...&join=myLeftJoin
```