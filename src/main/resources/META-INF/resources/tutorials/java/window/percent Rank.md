```java
default Column columnPercentRank() {	
	return Column.percentRank()
	.over(new PartitionComposer()
	.columns(myColumn())
	.orders(orderCol)
	.compose(store));	
}
```

```javascript
select=columnPercentRank:colPercentRank
```