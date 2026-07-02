```java
default Column columnRank() {	
	return Column.rank()
    .over(new PartitionComposer()
    .columns(myColumn())
    .orders(orderCol)
    .compose(store));	
}
```

```javascript
select=columnRank:colRank
```