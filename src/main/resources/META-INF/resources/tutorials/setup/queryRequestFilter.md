QueryRequestFilter allows you to define a controlled query.

Unlike <b>QueryRequest</b>:

- Users cannot override the query
- Only allowed parameters can be merged
- Unauthorized changes result in errors

You Will Learn:

- How to define a fixed query
- How parameters work
- How <b>Keyword</b> controls user access

1. Basic Usage

```java
@GetMapping("products")
public Map<String, Object> fetchProducts(
    @QueryRequestFilter(view = "product",column = "id,name,price") QueryComposer query) {
    return execute(DemoStore.class, query);
}
```

<b>Result</b>

```sql
SELECT v0.ID, v0.NAME, v0.PRICE FROM PRODUCT v0;
```

2. Parameters

| Parameter          | Type        | Description                    | Required  |
| ------------------ | ----------- | ------------------------------ | --------- |
| `view`             | `String`    | Target dataset (view identity) | `Yes`     |
| `database`         | `String`    | Store/database name            | `Yes`     |
| `column`           | `String[]`  | Selected columns               | `Yes`     |
| `filters`          | `String[]`  | Default filters applied        | `No`      |
| `join`             | `String[]`  | Default joins                  | `No`      |
| `order`            | `String[]`  | Default ordering               | `No`      |
| `distinct`         | `boolean`   | Enables DISTINCT               | `No`      |
| `limit`            | `int`       | Maximum rows                   | `No`      |
| `offset`           | `int`       | Starting row                   | `No`      |
| `ignoreParameters` | `String[]`  | URL params to ignore           | `No`      |
| `mergeParameters`  | `Keyword[]` | Allowed parameters from URL    | `No`      |
| `variables`        | `String[]`  | Variables from URL             | `No`      |

3. Default Behavior

By default:

- Query is locked
- URL parameters are not allowed

<b>Example</b>

```javascript
/products?select=id,name,cat_id
```

❌ Error

4. Keywords (Merge Control)

<b>mergeParameters</b> defines what users can modify.

| Keyword    | Description           |
| ---------- | --------------------- |
| `COLUMN`   | Allows adding columns |
| `DISTINCT` | Allows DISTINCT       |
| `JOIN`     | Allows adding joins   |
| `OFFSET`   | Allows offset         |
| `LIMIT`    | Allows limit          |
| `ORDER`    | Allows ordering       |

5. Example: Allow ORDER

```java
@GetMapping("products")
public Map<String, Object> fetchProducts(
    @QueryRequestFilter(view = "product",column = "id,name", order="name",mergeParameters = {Keyword.ORDER}) QueryComposer query) {
    return execute(DemoStore.class, query);
}
```

```javascript
/products?select=price&order=price
```

<b>Result</b>

```sql
SELECT v0.ID, v0.NAME, v0.PRICE FROM PRODUCT v0;
ORDER BY v0.NAME,v0.PRICE
```

In the Url it looks like this:

```javascript
/products?select=id,name,price&order=name,price
```

- ✔ merged, not replaced

6. Real Use Case

UI constraint:

- Table expects fixed columns
- User adds column → ❌ breaks UI

<b>Solution</b>

```java
@QueryRequestFilter(
    view = "product",
    column = {"id","name","price"}
)
```

- ✔ Query is fixed
- ✔ UI is safe

<b>Summary</b>

- Query is fixed
- Users cannot override
- mergeParameters allows controlled extensions
- Allowed parameters are merged

✅ Use QueryRequestFilter when you need control and stability