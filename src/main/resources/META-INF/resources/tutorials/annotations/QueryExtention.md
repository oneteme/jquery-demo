`@QueryExtension` allows you to customize how a `@QueryTemplate` behaves when request parameters are provided.

Instead of defining the query itself, `@QueryExtension` controls whether different parts of the query are **replaced**, **merged**, or **rejected**.

---

What You Will Learn

In this guide, you will learn how to:

- Extend a query template
- Control how request parameters are applied
- Configure query behavior

---

1. Add a Query Extension

Annotate your controller method with `@QueryExtension`.

<b>Example</b>

```java
@GetMapping("employees")
@QueryTemplate(
    dataset = "employees",
    select = "id,lname,fname,start"
)
@QueryExtension(
    select = Modifier.MERGE,
    order = Modifier.MERGE
)
public Object fetchEmployees(MvcRequest mvc) {
    return mvc.execute();
}
```

In this example, the default query is still defined by `@QueryTemplate`, while `@QueryExtension` specifies how request parameters interact with it.

---

2. Understanding the Modifiers

Each query section can use one of the following modifiers.

| Modifier  | Description                                                                                                                        |
| --------- | ---------------------------------------------------------------------------------------------------------------------------------- |
| `REPLACE` | Replaces the default value with the value provided in the request.                                                                 |
| `MERGE`   | Combines the default value with the value provided in the request.                                                                 |
| `REJECT`  | Rejects any attempt to modify this part of the query through the request. If a value is provided, the request fails with an error. |


For example, if the template defines:

```text
select = "id,name"
```

and the request contains:

```text
?select=city
```

the result depends on the modifier:

| Modifier | Generated Select |
|----------|------------------|
| `REPLACE` | `city` |
| `MERGE` | `id, name, city` |
| `REJECT` | ERROR |

---

3. Available Properties

| Attribute | Type | Description |
|-----------|------|-------------|
| `cte` | `Modifier` | Controls how CTEs are handled. |
| `select` | `Modifier` | Controls how selected columns are handled. |
| `filter` | `Modifier` | Controls how request filters are handled. |
| `join` | `Modifier` | Controls how joins are handled. |
| `order` | `Modifier` | Controls how ordering is handled. |
| `acceptCriteria` | `boolean` | Allows or rejects request criteria. |
| `overrideDistinct` | `boolean` | Allows the request to override the `distinct` option. |
| `overrideLimit` | `boolean` | Allows the request to override the query limit. |
| `overrideOffset` | `boolean` | Allows the request to override the query offset. |
| `overrideView` | `boolean` | Allows the request to override the response view. |

---

<b>Example</b>

```java
@GetMapping("employees")
@QueryTemplate(
    dataset = "employees",
    select = "id,lname,fname,start",
    order = "lname",
    limit = 20
)
@QueryExtension(
    select = Modifier.MERGE,
    order = Modifier.REJECT,
    overrideLimit = false
)
public Object fetchEmployees(MvcRequest mvc) {
    return mvc.execute();
}
```

This configuration:

- allows additional selected columns to be merged
- prevents the request from changing the default ordering
- prevents the request from overriding the limit

---

<b>Summary</b>

`@QueryExtension` controls how request parameters interact with a `@QueryTemplate`.

It lets you decide which parts of the query can be customized by the client and which parts should remain fixed.

---

✅ Use `@QueryExtension` whenever you need fine-grained control over how queries can be modified at runtime.