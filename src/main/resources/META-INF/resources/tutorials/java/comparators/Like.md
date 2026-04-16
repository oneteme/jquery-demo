```java
default Criteria columnLike(String pattern) {
	return column().like(pattern);
}
```

```javascript
select=...&columnLike(pattern)
```