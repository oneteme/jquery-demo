```java
default Criteria columnIlike(String pattern) {
	return column().ilike(pattern);
}
```

```javascript
select=...&columnIlike(pattern)
```