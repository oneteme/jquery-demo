```java
// CASE 1
// Two seperate JoinClauses with a single join each

// Check previous joins for ViewJoin syntax
default JoinsClause join1() {
	return joins(ViewJoin);
}

default JoinsClause join2() {
	return joins(ViewJoin);
}
```

```javascript
// CASE 1
select=...&join=join1,join2
```

```java
// CASE 2
// One JoinClause but with multiple joins

// Check previous joins for ViewJoin syntax
default JoinsClause myMultipleJoin() {
	return joins(ViewJoin1, ViewJoin2, ...);
}
```

```javascript
// CASE 2
select=...&join=myMultipleJoin
```
