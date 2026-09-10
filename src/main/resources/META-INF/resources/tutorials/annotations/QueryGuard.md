### QueryGuard annotation guide

`@QueryGuard` allows you to define validation rules for a query.

It is used together with `@QueryTemplate` to ensure that the generated query complies with your application's requirements before it is executed.

---

What You Will Learn

In this guide, you will learn how to:

- Add validation rules to a query
- Restrict accessible resources
- Limit query size
- Restrict supported database dialects

---

1. Add a Query Guard

Annotate your controller method with `@QueryGuard`.

<b>Example</b>

```java
@GetMapping("employees")
@QueryGuard(
    maxRows = 100
)
@QueryTemplate(
    dataset = "employees",
    select = "id,lname,fname,start"
)
public Object fetchEmployees(MvcRequest mvc) {
    return mvc.execute();
}
```

This query will execute only if the returned result contains **100 rows or fewer**.

---

2. Available Properties

| Attribute | Type | Description |
|-----------|------|-------------|
| `excludeResources` | `String[]` | Prevents specific datasets or fields from being used in the query. |
| `excludeDialects` | `String[]` | Prevents the query from being executed on specific SQL dialects. |
| `aggregate` | `boolean` | Rejects non-aggregate queries when enabled. |
| `maxRows` | `int` | Rejects queries that returns rows more than `maxrows` |
| `maxCols` | `int` | Rejects queries that have columns more than `maxCols` |

---

3. Restrict Resources

You can prevent users from accessing specific tables or databases.

<b>Example</b>

```java
@QueryGuard(
    excludeResources = {
        "customers",
        "orders"
    }
)
```

Any query attempting to use these resources will be rejected.

---

4. Restrict Dialects

You can prevent a query from running on specific database dialects.

<b>Example</b>

```java
@QueryGuard(
    excludeDialects = {
        "mysql",
        "oracle"
    }
)
```

If the current database matches one of the excluded dialects, the query will be rejected.

---

5. Limit Query Size

You can restrict the maximum number of rows and columns returned.

<b>Example</b>

```java
@QueryGuard(
    maxRows = 500,
    maxCols = 10
)
```

The query will execute only if both limits are respected.

---

6. Require Aggregate Queries

Some endpoints should only allow aggregate queries.

<b>Example</b>

```java
@QueryGuard(
    aggregate = true
)
```

If the generated query is not an aggregate query, the request will be rejected.

---

<b>Summary</b>

`@QueryGuard` validates a query before it is executed.

It allows you to:

- restrict accessible resources
- restrict supported SQL dialects
- require aggregate queries
- limit the number of returned rows and columns

---

✅ Use `@QueryGuard` to enforce security and validation rules on your query endpoints.