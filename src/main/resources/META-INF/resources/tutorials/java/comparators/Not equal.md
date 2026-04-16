```java
// Criteria function parameters are fully customizable [...args]
default Criteria columnNotEqual(String value) {
	return column().ne(value);
}
```

```javascript
select=...&columnNotEqual(value)
```