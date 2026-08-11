A Criteria represents a complete condition that can be applied to a query. It is equivalent to a WHERE clause in SQL.

Example:
```sql
WHERE price = 10
```

In this guide, you will learn how to:

- Create a basic criteria
- Create dynamic criteria with parameters

1. Create a Basic Criteria

Criteria are defined inside a Dataset.

<b>Example</b>
```java
// In your DatasetCatalogue
default Criteria priceEqualsTen() {
    return price().eq(10);
}
```

When used in a query, this generates:
```sql
WHERE price = 10
```

2. Create Dynamic Criteria

Instead of hardcoding values, you can pass parameters.

Example: 

```java 
// In your DatasetCatalogue
default Criteria priceRangeByName(String name, Integer v1, Integer v2) {
	return price().between(arr).and(name().contentLike(name));
}
```

Usage:
```javascript
select=...&priceRangeByName(ef,10,20,30)
```
When used in a query, this generates:
```sql
WHERE
  (
    v1.PRICE > 10
    AND v1.PRICE < 30
    AND v1.PRODUCT_NAME LIKE '%ef%'
  )
```

- A Criteria is used to filter query results
- It is defined inside a Dataset and It can be:
    - static (fixed value)
    - dynamic (with parameters)
    - combined with other conditions

✅ You can now create reusable conditions to control what data your queries return.