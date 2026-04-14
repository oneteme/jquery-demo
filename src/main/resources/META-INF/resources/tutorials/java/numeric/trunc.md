```java
default Column columnTrunc() {
	return column().trunc(decimal_places); // OPTIONAL : [decimal_places]
}
```

```javascript
select=columnTrunc:colTruncate
```