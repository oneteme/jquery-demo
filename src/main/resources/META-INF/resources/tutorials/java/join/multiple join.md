```java
// CASE 1
// Two seperate JoinGroups with a single join each

// Check previous joins for Join syntax
default JoinGroup join1() {
	return joins(ViewJoin);
}

default JoinGroup join2() {
	return joins(ViewJoin);
}
```

```javascript
// CASE 1
select=...&join=join1,join2
```

```java
// CASE 2
// One JoinGroup but with multiple joins

// Check previous joins for Join syntax
default JoinGroup myMultipleJoin() {
	return joins(Join1, Join2, ...);
}
```

```javascript
// CASE 2
select=...&join=myMultipleJoin
```
