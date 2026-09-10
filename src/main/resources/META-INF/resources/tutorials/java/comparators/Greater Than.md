```java
// Criteria function parameters are fully customizable [...args]
default Criteria columnGreaterThan(String value) {
	return myColumn().gt(value);
}
```

```javascript
select=...&columnGreaterThan(value)
```