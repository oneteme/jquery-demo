```java
// Criteria function parameters are fully customizable [...args]
default Criteria columnLowerEqual(String value) {
	return myColumn().le(value);
}
```

```javascript
select=...&columnLowerEqual(value)
```