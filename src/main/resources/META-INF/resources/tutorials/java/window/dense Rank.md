```java
default Column columnDenseRank() {	
	return Column.denseRank()
	.over(new PartitionComposer()
	.columns(myColumn())
	.orders(orderCol)
	.compose(store));	
}
```

```javascript
select=columnDenseRank:colDenseRank
```