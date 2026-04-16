```java
// Criteria function parameters are fully customizable [...args]
default Criteria columnNotIn(Integer ...values) {
	return column().notIn(values);
}
```

```javascript
select=...&columnNotIn(val1,val2,val3,...)
```