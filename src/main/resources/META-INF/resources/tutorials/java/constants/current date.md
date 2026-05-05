```java
default Column getDate() {
	return Dialect.getDialect().cdate().invoke();
}
```

```javascript 
select=getDate:current_Date
```