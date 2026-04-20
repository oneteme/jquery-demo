```java	
// v0 is the DatasetResource that contains the JoinClause
default JoinsClause myRightJoin() {
    var v1 = getInstance().getStore(myStore.class).view1();
	return joins(rightJoin(v1.getView(), column().eq(v1.id())));
}
```