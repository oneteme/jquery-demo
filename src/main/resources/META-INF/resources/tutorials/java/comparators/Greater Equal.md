```java
// Criteria function parameters are fully customizable [...args]
default Criteria columnGreaterEqual(String value) {
	return myColumn().ge(value);
}
```

```javascript
select=...&columnGreaterEqual(value)
```