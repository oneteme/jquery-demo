QueryRequest allows you to build queries directly from your controller. It provides default query parameters, but these can be overridden by the user through the URL.

<b>QueryRequest</b>: 

- It is flexible
- Users can override it via URL parameters
- Best used for dynamic queries

You Will Learn:

- How to use QueryRequest
- How parameters work
- How URL overrides behave

1. Basic Usage

```java
@GetMapping("products")
public Map<String, Object> fetchProducts(
    @QueryRequest(dataset = "products", fields = "id,name,price") QueryComposer query) {
    return execute(DemoStore.class, query);
}
```

<b>Result</b>

```sql
SELECT v0.ID, v0.NAME,v0.PRICE FROM PRODUCTS_TABLE v0;
```

2. Parameters

| Parameter          | Type            | Description                           | Required  |
| ------------------ | --------------- | ------------------------------------- | --------- |
| `dataset`          | `String`        | Target dataset (view name)            | `Yes`     |
| `store`            | `StoreResource` | Store to use                          | `Yes`     |
| `fields`           | `String[]`      | Default selected columns              | `Yes`     |
| `ignore`           | `String[]`      | Request parameters to ignore          | `No`      |
| `aggregate`        | `boolean`       | Forces aggregation queries            | `No`      |
| `maxSize`          | `int`           | Maximum number of rows returned       | `No`      |
| `excludeViews`     | `String[]`      | Views that cannot be used             | `No`      |
| `excludeResources` | `String[]`      | Resources to exclude                  | `No`      |
| `excludeDialects`  | `String[]`      | Dialects to exclude                   | `No`      |

3. URL Overrides

Users can override the query using URL parameters.

<b>Example</b>
```javascript
/products?select=id,name
```

<b>Result</b>

```sql
SELECT v0.ID, v0.NAME FROM PRODUCTS_TABLE v0;
```

- Controller values = default
- URL parameters = override
- The user controls the final query

4. Example

```java
@GetMapping("products")
public Map<String, Object> fetchProducts(
    @QueryRequest(dataset = "products", fields = "id,name,price") QueryComposer query) {
    return execute(DemoStore.class, query);
}
```

Now let's override the columns and add an order to this query

```javascript
/products?select=name,price&order=price.asc
```

<b>Result</b>

```sql
SELECT v0.NAME,v0.PRICE FROM PRODUCTS_TABLE v0;
ORDER BY v0.PRICE ASC
```

<b>Summary</b>

- <b>QueryRequest</b> is flexible
- Users can override parameters via URL
- Ideal for dynamic queries

✅ Use QueryRequest when users should control the query