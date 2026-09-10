```java
// Count function can be called without "myColumn()" to get view row number
default Column columnCount() {
	return myColumn().count();
}
```

```javascript
select=columnCount:colCount
```