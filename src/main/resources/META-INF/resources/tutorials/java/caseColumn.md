```java
// With Criteria using "beginCase"
default CaseColumn columnWhen() {
	return Column.beginCase()
    .when(Crit1, val1).when(Crit2, val2)... .orElse(valn);
}
```

```java
// With Predicate
default CaseColumn columnWhen() {
	return myColumn().toCase()
    .when(Pred1, val1).when(Pred2, val2)... .orElse(valn);
}
```
<!-- playdata: {"title":"","view":"products","column":"price,whenCol:price_lab"} -->
```javascript
select=columnWhen:caseCol
```