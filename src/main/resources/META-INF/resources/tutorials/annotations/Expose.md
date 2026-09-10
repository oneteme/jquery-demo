# Expose annotation guide

`@Expose` is used to customize how resources are exposed to API queries.

It can be used with columns, datasets, `JoinGroup`, `Criteria`, custom functions, and other resources.

This annotation allows you to:

- rename a resource for API queries
- add documentation
- define an SQL alias
- hide resources from public usage

---

## What You Will Learn

In this guide, you will learn how to:

- Customize columns and datasets using `@Expose`
- Use `alias` with `@Expose`
- Use `@Expose` with `JoinGroup`, `Criteria`, and custom functions
- Use `identity`, `alias`, and `description`
- Hide resources from public usage

---

1. Customizing Columns and Datasets using `@Expose`

Columns and datasets can be customized using the `@Expose` annotation.

### Columns

```java
@Expose(
    identity = "customer_address",
    description = "Customer's home address"
)
@Bind("ADDRESS")
ViewColumn address();

@Expose(false)
@Bind("CONTACT_NAME")
ViewColumn contact();
```

| Database Column | Exposed | JQuery Column |
|-----------------|---------|---------------|
| `ADDRESS` | Yes | `customer_address` |
| `CONTACT_NAME` | No | Not accessible |

The customized column can then be used in API queries:

```text
/customers?select=customer_address
```

In Java, the column is still accessed using:

```java
Customers.address();
```

### Datasets

```java
@Expose(
    identity = "orders_details",
    description = "More details about the order like the quantity and order date"
)
@Bind("ORDERS_DETAILS_TABLE")
OrdersDetails ordersDetails();

@Expose(false)
@Bind("EMPLOYEES_TABLE")
Employees employees();
```

| Database Table | Exposed | Query Name |
|----------------|---------|------------|
| `ORDERS_DETAILS_TABLE` | Yes | `orders_details` |
| `EMPLOYEES_TABLE` | No | Not accessible |

The exposed dataset can then be used in API queries with its customized name:

```text
/orders_details
```

---

2. Using `alias` with `@Expose`

The `alias` parameter defines the SQL alias for a resource when the SQL is generated.

It can be used on both columns and datasets.

### Column

```java
@Expose(
    identity = "customer_address",
    alias = "address"
)
@Bind("ADDRESS")
ViewColumn address();
```

If the column is selected, the generated SQL can use the alias:

```sql
SELECT
    ADDRESS AS address
FROM
    CUSTOMERS_TABLE;
```

### Dataset

```java
@Expose(
    identity = "orders_details",
    alias = "orders"
)
@Bind("ORDERS_DETAILS_TABLE")
OrdersDetails ordersDetails();
```

The generated SQL can use:

```sql
SELECT
    orders.*
FROM
    ORDERS_DETAILS_TABLE orders;
```

Here:

- `identity` is the name used in API queries.
- `alias` is the SQL alias.
- `ORDERS_DETAILS_TABLE` is the real database table.

| Parameter | Purpose |
|-----------|---------|
| `identity` | Name used in API queries |
| `alias` | Alias used in generated SQL |

---

3. Using `@Expose` with Other Resources

`@Expose` is not limited to columns and datasets.

It can also be used with other JQuery resources, such as:

- `JoinGroup`
- `Criteria`
- custom functions
- other exposed resources

<b>Example</b>

```java
@Expose(
    identity = "customer_orders",
    description = "Joins customers with their orders"
)
default JoinGroup customerOrders();
```

The same idea can be applied to a `Criteria`:

```java
@Expose(
    identity = "active_customers",
    description = "Returns only active customers"
)
default Criteria activeCustomers();
```

And to a custom function:

```java
@Expose(
    identity = "pow",
    description = "Raises a number to a specified power"
)
default OperatorDefinition pow() {
    return function(
        DOUBLE,
        "POW",
        required(DOUBLE),
        required(DOUBLE)
    );
}
```

---

4. Hiding Resources

A column, dataset, JoinGroup, Criteria, custom function, or other exposed resource can be hidden by using:

```java
@Expose(false)
```

<b>Example</b>

```java
@Expose(false)
Criteria myCriteria();
```

The resource remains available in the Java definition but is not exposed for public API usage.

---

5. `@Expose` Parameters

The `@Expose` annotation provides four parameters.

| Parameter | Type | Description |
|-----------|------|-------------|
| `value` | `boolean` | Controls whether the resource is exposed. |
| `identity` | `String` | Name used for the resource in API queries. |
| `alias` | `String` | SQL alias for the resource. |
| `description` | `String` | Description of the resource. **(Documentation)** |

<b>Example</b>

```java
@Expose(
    identity = "customer_address",
    alias = "address",
    description = "Customer's home address"
)
@Bind("ADDRESS")
ViewColumn address();
```

By default, `value` is `true`.

If `identity` is not specified, the Java method name is used as the resource name.

---

<b>Summary</b>

`@Expose` allows you to customize how JQuery resources are exposed.

It can be used with:

- Columns
- Datasets
- JoinGroup
- Criteria
- Custom functions
- Other exposed resources

You can use it to:

- rename resources with `identity`
- define SQL aliases with `alias`
- add descriptions with `description`
- hide resources with `@Expose(false)`

---

✅ You can now use `@Expose` to customize, document, rename, alias, or hide the different resources available in JQuery.