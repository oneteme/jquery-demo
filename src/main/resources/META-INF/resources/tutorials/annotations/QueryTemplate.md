`@QueryTemplate` allows you to define a query directly on a controller method.

Instead of building the query manually, you specify the dataset and the default query options through the annotation. The query is then executed automatically using `MvcRequest`.

---

What You Will Learn

In this guide, you will learn how to:

- Create a query template
- Define the dataset and selected columns
- Execute the query

---

1. Create a Query Template

Annotate your controller method with `@QueryTemplate`.

The only required properties are:

- `dataset` – the dataset to query.
- `select` – the columns to return.

<b>Example</b>

```java
@GetMapping("employees")
@QueryTemplate(
    dataset = "employees",
    select = "id,lname,fname,start,photo,notes"
)
public Object fetchEmployees(MvcRequest mvc) {
    return mvc.execute();
}
```

The query will automatically retrieve the specified columns from the `employees` dataset.

---

2. Execute the Query

Inside the controller method, simply return:

```java
return mvc.execute();
```

`MvcRequest` reads the `@QueryTemplate` configuration, builds the query, applies any request parameters, and executes it.

---

3. Customizing the Query

`@QueryTemplate` provides several optional attributes to customize the generated query.

| Attribute | Type | Description |
|-----------|------|-------------|
| `dataset` | `String` | Default table for the current request. |
| `store` | `StoreCatalog` | The store where the query will be executed. |
| `cte` | `String[]` | Declare one or more query as Common Table Expressions (CTEs). |
| `select` | `String[]` | Columns to return. |
| `join` | `String[]` | Joins to include. |
| `criteria` | `String[]` | Default filtering conditions merged with URL filters. |
| `order` | `String[]` | Default ordering. |
| `distinct` | `boolean` | Returns distinct rows. |
| `limit` | `int` | Maximum number of rows to return. |
| `offset` | `int` | Number of rows to skip before returning results. |
| `ignore` | `String[]` | Request parameters ignored by the `JQuery`. |
| `view` | `String` | Response view used to render the result. |

---

<b>Example</b>

```java
@GetMapping("employees")
@QueryTemplate(
    dataset = "employees",
    select = "id,lname,fname,start",
    criteria = "active",
    order = "lname",
    limit = 20,
    view = "debug"
)
public Object fetchEmployees(MvcRequest mvc) {
    return mvc.execute();
}
```

This template:

- queries the `employees` dataset
- returns the selected columns
- applies the `active` criteria
- sorts the results by `lname`
- limits the output to 20 rows
- renders the response using the `debug` view

---

<b>Summary</b>

`@QueryTemplate` provides a simple way to define reusable queries directly in your controllers.

It allows you to configure the query once while still letting `MvcRequest` apply additional filters and options provided through the request.

---

✅ Your endpoint is now ready to execute JQuery queries with minimal controller code.