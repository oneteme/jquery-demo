```java
// With Criteria using "beginCase"
import static org.usf.jquery.core.Column.beginCase;
default CaseColumn columnWhen() {
	return Column.beginCase()
    .when(Crit1, val1).when(Crit2, val2)... .orElse(valn);
}
```

```java
// With Predicate
default CaseColumn columnWhen() {
	return column().toCase()
    .when(Pred1, val1).when(Pred2, val2)... .orElse(valn);
}
```

```javascript
select=columnWhen:caseCol
```