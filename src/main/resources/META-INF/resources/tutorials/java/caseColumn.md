```java
default CaseColumn columnWhen() {
	return column()
            .toCase()
            .when(Predicate1, val1)
            .when(Predicate2, val2)
            ...
            .orElse(valn);
}
```

```javascript
select=columnWhen:caseCol
```