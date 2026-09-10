```java
// Criteria function parameters are fully customizable [...args]
default Criteria columnEqual(String value) {
	return myColumn().eq(value);
}
```

```javascript
select=...&columnEqual(value)
```