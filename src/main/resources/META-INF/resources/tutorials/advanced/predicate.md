A Predicate defines a condition that can be applied to a column.
It allows you to customize how values are interpreted in a query.

In this guide, you will learn how to:

- Create a simple predicate
- Create a dynamic predicate
- Create an advanced predicate

1. Create a Simple Predicate

Let’s create a predicate that checks if a value is greater than 10.

```java
// In your StoreResource
@Expose(identity="Gt_10")
default Predicate greaterThanTen() {
    return gt(10);
}
```

<b> Usage </b>
```javascript
select=...&price.Gt_10()
```

<b> Result </b>
```sql
WHERE price < 10
```

2. Create a Dynamic Predicate

A dynamic predicate accepts a parameter.

<b>Example</b>

```java
// In your StoreResource
@Expose(identity="greaterThan")
default Predicate greaterThan(Double value) {
    return gt(value);
}
```

<b> Usage </b>
```javascript
select=...&price.greaterThan(50)
```

<b> Result </b>
```sql
WHERE price > 50
```

3. Create an Advanced Predicate

An advanced predicate can map multiple values to different conditions using <b>Chainable</b>.

<b>Example</b>

```java
// In your StoreResource
@Expose(identity="category") 
default Predicate priceCategory(String... values) {
    return Chainable.or(values, v -> switch (v) {
        case "cheap" -> lt(10);
        case "medium" -> ge(10).and(lt(20));
        case "expensive" -> ge(20);
        default -> null;
    });
}
```

<b> Usage 1</b>
```javascript
select=...&price.category(cheap)
```

<b> Result </b>
```sql
WHERE v0.PRICE < 10
```

<b> Usage 2</b>
```javascript
select=...&price.category(cheap,medium)
```

<b> Result </b>
```sql
WHERE
  (
    v0.PRICE < 10
    OR (
      v0.PRICE >= 10
      AND v0.PRICE < 20
    )
  )
```

<b> Summary </b>

- A Predicate defines how a condition is applied to a column
- It is created inside a Store and it can be:
    - simple (fixed condition)
    - dynamic (with parameters)
    - advanced (mapping multiple values)

✅ Predicates allow you to build flexible and reusable filtering logic.