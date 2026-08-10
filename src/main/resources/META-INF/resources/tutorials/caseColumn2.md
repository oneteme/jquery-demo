1. Syntax (URL)

```scss
when(criteria1,val1).when(criteria2,val2)...orElse(valn)
```
<!-- playdata: {"title": "Use CASE column in <b>URL</b>",
"view": "products",
"column": "price,when(price.lt(10),Cheap).when(price.ge(10).and(price.lt(20)),Normal).orElse(Expensive):cost_case"} -->

```scss
/products?select=price,when(price.lt(10),Cheap).when(price.ge(10).and(price.lt(20)),Normal).orElse(Expensive):cost_case
```
2. Declarations (Java)

`beginCase`

```java
// With Criteria using "beginCase"
default CaseColumn columnWhen() {
	return Column.beginCase()
    .when(Crit1, val1)
    .when(Crit2, val2)
    ... 
    .orElse(valn);
}
```

<!-- playdata: {"title":"Using when with beginCase from JAVA ","view":"products","column":"price,priceWhen:cost_case"} -->
```java
default CaseColumn priceWhen() {
	return Column.beginCase()
    .when(price().lt(10), "Cheap")
    .when(price().ge(10).and(price().lt(20)), "Normal")
    .orElse("Expensive");
}
```

`myColumn().toCase`

```java
// With Predicate
default CaseColumn columnWhen() {
	return myColumn().toCase()
    .when(Pred1, val1)
    .when(Pred2, val2)
    ... 
    .orElse(valn);
}
```

<!-- playdata: {"title":"Using when with toCase from JAVA ","view":"products","column":"price,priceToCase:cost_case"} -->
```java
default CaseColumn priceToCase() {
	return price().toCase()
    .when(lt(10), "Cheap")
    .when(ge(10).and(lt(20)), "Normal")
    .orElse("Expensive");
}
```