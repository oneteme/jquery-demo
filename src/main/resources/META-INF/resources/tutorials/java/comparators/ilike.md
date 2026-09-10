```java
default Criteria columnIlike(String pattern) {
	return myColumn().iLike(pattern);
}
```

```javascript
select=...&columnIlike(pattern)
```