```java
default Criteria columnEndsLike(String pattern) {
	return myColumn().endsLike(pattern);
}
```

```javascript
select=...&columnEndsLike(pattern)
```