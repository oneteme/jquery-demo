```java
default Criteria columnOr() {
	// filter example : price().eq(10)
	return filter1.or(filter2.or(filter3).or(etc...)).or(filter4);
}
```

```javascript
select=...&columnOr
```