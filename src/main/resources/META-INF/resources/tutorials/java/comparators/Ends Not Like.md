```java
default Criteria columnEndsNotLike(String pattern) {
	return myColumn().endsNotLike(pattern);
}
```

```javascript
select=...&columnEndsNotLike(pattern)
```