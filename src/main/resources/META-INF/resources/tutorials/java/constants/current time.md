```java
default Column getTime() {
	return Dialect.getDialect().ctime().invoke();
}
```

```javascript 
select=getTime:current_time
```