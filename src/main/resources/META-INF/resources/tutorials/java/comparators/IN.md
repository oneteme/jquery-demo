```java
// Criteria function parameters are fully customizable [...args]
default Criteria columnIn(Integer ...values) {
	return column().in(values);
}
```

```javascript
select=...&columnIn(val1,val2,val3,...)
```