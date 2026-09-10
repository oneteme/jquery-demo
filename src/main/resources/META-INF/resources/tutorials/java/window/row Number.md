```java
default Column columnRowNumber() {	
	return Column.rowNumber()
	.over(new PartitionComposer()
	.columns(myColumn())
	.orders(orderCol)
	.compose(store));	
}
```

```javascript
select=columnRowNumber:colRowNumber
```