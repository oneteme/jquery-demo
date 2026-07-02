```java
// Ascending Order
default Order orderColAsc() {
	return myColumn().asc();
}
```

```java
// Descending Order
default Order orderColDesc() {
	return myColumn().desc();
}
```

```javascript
select=...&order=orderColAsc,orderColDesc,...
```