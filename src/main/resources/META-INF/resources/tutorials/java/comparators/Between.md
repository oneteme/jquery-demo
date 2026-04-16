```java
// Criteria function parameters are fully customizable [...args]
default Criteria columnBetween(Integer val1, Integer val2) {
	return column().between(val1, val2);
}
```

```javascript
select=...&columnLowerThan(val1, val2)
```