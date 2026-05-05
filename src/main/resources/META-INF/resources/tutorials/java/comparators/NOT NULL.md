```java
default Criteria columnNotNull() {
	return myColumn().notNull();
}
```

```javascript
select=...&columnNotNull
```