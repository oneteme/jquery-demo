```java
default Column getTimestamp() {
	return Dialect.getDialect().ctimestamp().invoke();
}
```

```javascript 
select=getTimestamp:current_timeStamp
```