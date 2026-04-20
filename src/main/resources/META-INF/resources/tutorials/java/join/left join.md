```java	
// v0 is the DatasetResource that contains the JoinClause
default JoinsClause myLeftJoin() {
    var v1 = getInstance().getStore(myStore.class).view1();
	return joins(leftJoin(v1.getView(), column().eq(v1.id())));
}
```