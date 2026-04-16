```java
// Criteria function parameters are fully customizable [...args]
default Criteria columnLowerThan(String value) {
	return column().lt(value);
}
```

```javascript
select=...&columnLowerThan(value)
```