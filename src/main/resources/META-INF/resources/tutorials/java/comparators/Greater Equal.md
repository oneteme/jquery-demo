```java
// Criteria function parameters are fully customizable [...args]
default Criteria columnGreaterEqual(String value) {
	return column().ge(value);
}
```

```javascript
select=...&columnGreaterEqual(value)
```