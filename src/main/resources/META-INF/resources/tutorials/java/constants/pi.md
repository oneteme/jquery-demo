```java
default Column getPi() {
	return Dialect.getDialect().pi().invoke();
}
```

```javascript
select=getPi:pi
```