```java
default Criteria columnIsNull() {
	return column().isNull();
}
```

```javascript
select=...&columnIsNull
```