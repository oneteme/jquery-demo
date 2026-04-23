```java
import org.usf.jquery.core.Dialect;

default Column getPi() {
	return Dialect.getDialect().pi().invoke();
}
```

```javascript
select=getPi:pi
```