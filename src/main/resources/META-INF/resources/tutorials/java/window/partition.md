```java
default Partition myPartition() {
	return new PartitionComposer().columns(myColumn).orders(myOrder).compose(myStore);
}
```

```javascript
select=function.over(myPartition)
```