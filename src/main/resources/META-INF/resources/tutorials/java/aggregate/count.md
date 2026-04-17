```java
// Count function can be called without "column()" to get view row number
default Column columnCount() {
	return column().count();
}
```

```javascript
select=columnCount:colCount
```