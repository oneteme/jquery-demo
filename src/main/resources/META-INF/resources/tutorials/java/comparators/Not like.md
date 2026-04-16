```java
default Criteria columnNotLike(String pattern) {
	return column().notlike(pattern);
}
```

```javascript
select=...&columnNotLike(pattern)
```