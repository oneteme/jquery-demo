```java
default Criteria columnStartsNotLike(String pattern) {
	return myColumn().startsNotLike(pattern);
}
```

```javascript
select=...&columnStartsNotLike(pattern)
```