```java
default Criteria columnLike(String pattern) {
	return myColumn().like(pattern);
}
```

```javascript
select=...&columnLike(pattern)
```