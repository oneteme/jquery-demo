Evaluates multiple conditions and returns a value depending on which condition is met.<br>The CASE expression works like an if-else structure in SQL. Each <b>WHEN</b> condition is evaluated in order, and the result of the first matching condition is returned.<br>If none of the conditions are satisfied, the <b>ELSE</b> value is returned.<br>This function is commonly used to categorize or transform values directly in a query.<br><br>Example use case: categorizing products based on their price (cheap, normal, expensive).",

1. Syntax (URL)

```scss
when(criteria1,val1).when(criteria2,val2)...orElse(valn)
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

3. Use cases

`Use CASE column in URL`
<!-- playdata: {"title": "Use CASE column in <b>URL</b>",
"view": "products",
"column": "price,when(price.lt(10),Cheap).when(price.ge(10).and(price.lt(20)),Normal).orElse(Expensive):cost_case"} -->

```scss
/products?select=price,when(price.lt(10),Cheap).when(price.ge(10).and(price.lt(20)),Normal).orElse(Expensive):cost_case
```

`Using when with beginCase from JAVA`

<!-- playdata: {"title":"Using when with beginCase from JAVA","view":"products","column":"price,priceWhen:cost_case"} -->
```java
default CaseColumn priceWhen() {
	return Column.beginCase()
    .when(price().lt(10), "Cheap")
    .when(price().ge(10).and(price().lt(20)), "Normal")
    .orElse("Expensive");
}
```

`Using when with toCase from JAVA`

<!-- playdata: {"title":"Using when with toCase from JAVA","view":"products","column":"price,priceToCase:cost_case"} -->
```java
default CaseColumn priceToCase() {
	return price().toCase()
    .when(lt(10), "Cheap")
    .when(ge(10).and(lt(20)), "Normal")
    .orElse("Expensive");
}
```