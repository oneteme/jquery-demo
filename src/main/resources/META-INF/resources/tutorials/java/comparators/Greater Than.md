```java
// Criteria function parameters are fully customizable [...args]
default Criteria columnGreaterThan(String value) {
	return column().gt(value);
}
```

```javascript
select=...&columnGreaterThan(value)
```