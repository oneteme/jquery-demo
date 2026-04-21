```java
default Column columnTag() {
	return column().as("new_col_name");
}
```

```javascript
select=columnTag
```