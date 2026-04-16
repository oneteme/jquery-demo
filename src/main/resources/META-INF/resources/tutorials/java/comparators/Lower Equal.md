```java
// Criteria function parameters are fully customizable [...args]
default Criteria columnLowerEqual(String value) {
	return column().le(value);
}
```

```javascript
select=...&columnLowerEqual(value)
```