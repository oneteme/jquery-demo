```java
// Criteria function parameters are fully customizable [...args]
default Criteria columnEqual(String value) {
	return column().eq(value);
}
```

```javascript
select=...&columnEqual(value)
```