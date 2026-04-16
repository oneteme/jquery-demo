```java
default Criteria columnStartsNotLike(String pattern) {
	return column().startsNotLike(pattern);
}
```

```javascript
select=...&columnStartsNotLike(pattern)
```