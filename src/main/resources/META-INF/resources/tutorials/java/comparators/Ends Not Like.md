```java
default Criteria columnEndsNotLike(String pattern) {
	return column().endsNotLike(pattern);
}
```

```javascript
select=...&columnEndsNotLike(pattern)
```