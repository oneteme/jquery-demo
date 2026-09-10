```java
default Criteria columnIsNull() {
	return myColumn().isNull();
}
```

```javascript
select=...&columnIsNull
```