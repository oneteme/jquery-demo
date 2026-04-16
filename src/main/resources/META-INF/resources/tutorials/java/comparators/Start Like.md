```java
default Criteria columnStartsLike(String pattern) {
	return column().startsLike(pattern);
}
```

```javascript
select=...&columnStartsLike(pattern)
```