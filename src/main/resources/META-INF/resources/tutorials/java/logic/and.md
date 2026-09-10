```java
default Criteria columnAnd() {
	// filter example : price().eq(10)
	return filter1.and(filter2.and(filter3).and(etc...)).and(filter4);
}
```

```javascript
select=...&columnAnd
```