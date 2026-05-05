```java
default Column columnTag() {
	return myColumn().as("new_col_name");
}
```

```javascript
select=columnTag
```