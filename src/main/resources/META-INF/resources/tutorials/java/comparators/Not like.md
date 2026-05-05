```java
default Criteria columnNotLike(String pattern) {
	return myColumn().notlike(pattern);
}
```

```javascript
select=...&columnNotLike(pattern)
```