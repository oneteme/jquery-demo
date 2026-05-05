```java
// Criteria function parameters are fully customizable [...args]
default Criteria columnNotEqual(String value) {
	return myColumn().ne(value);
}
```

```javascript
select=...&columnNotEqual(value)
```