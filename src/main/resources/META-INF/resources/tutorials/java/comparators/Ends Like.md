```java
default Criteria columnEndsLike(String pattern) {
	return column().endsLike(pattern);
}
```

```javascript
select=...&columnEndsLike(pattern)
```