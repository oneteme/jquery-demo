```java
default Criteria columnStartsLike(String pattern) {
	return myColumn().startsLike(pattern);
}
```

```javascript
select=...&columnStartsLike(pattern)
```