```java
default Column getRandom() {
	return Dialect.getDialect().random().invoke();
}
```

```javascript 
select=getRandom:random
```