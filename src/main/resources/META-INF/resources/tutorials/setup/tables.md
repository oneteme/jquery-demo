### Bind Dataset Catalogs

This guide explains how to configure a Store in `JQuery`.

A Store acts as the entry point of your API, grouping:

- all datasets (tables / views)
- custom functions
- controlling what is exposed to the API

The setup consists of two main steps:

- Bind datasets (views)
- Define or override functions

For this guide we will be using the previously created **StoreCatalog** called **DemoStore**.

```java
// DemoStore.java

public interface DemoStore extends StoreResource {

}
```

1. Bind Datasets (Views)

Inside the store, declare all datasets you want to expose.

Each dataset:

- represents a table or view
- must be linked using `@Bind`
- returns a previously defined `DatasetCatalogue`

<b>Basic Syntax</b>

 ```java 
 //Sample
@Bind("REAL_TABLE_NAME")
MyDataset datasetName();
 ```

| Element           | Description                     |
| ----------------- | ------------------------------- |
| `REAL_TABLE_NAME` | Table/view name in the database |
| `datasetName()`   | Name used in Java and queries   |

<b>Example</b>

 ```java
//Customers.java

public interface DemoStore extends StoreResource {

	@Bind("CUSTOMERS_TABLE")
	Customers customers();

	@Bind("SHIPPERS_TABLE")
	Shippers shippers();

	@Bind("categories")
	Categories categories();

	// other datasets
	// create new functions
	// override existing functions
}

//Customers.java
 ```

| Store Method   | Database Table     |
| -------------- | ------------------ |
| `customers()`  | `CUSTOMERS_TABLE`  |
| `shippers()`   | `SHIPPERS_TABLE`   |
| `categories()` | `CATEGORIES_TABLE` |

These datasets are now accessible through the Store.

2. Customize Dataset Exposure

Like columns, datasets can be customized using `@Expose`.

This allows you to:

- rename a dataset in queries
- document it
- hide it from users

 ```java
@Expose(identity="orders_details", description="More details about the order like the quantity and order date")
@Bind("ORDERS_DETAILS_TABLE")
OrdersDetails ordersDetails();
 ```

 ```java
@Expose(false)
@Bind("EMPLOYEES_TABLE")
Employees employees();
 ```

| Database Table         | Exposed | Query Name       |
| ---------------------- | ------- | ---------------- |
| `ORDERS_DETAILS_TABLE` | Yes     | `orders_details` |
| `EMPLOYEES_TABLE`      | No      | Not accessible   |

3. Function Mapping

The `Store` defines how JQuery functions are translated into SQL.

You are not creating or overriding SQL functions.

Instead, you define how a function used in a query is converted into SQL.

<b>Basic Syntax</b>

```java
default OperatorDefinition functionName() {
	return function(RETURN_TYPE, "SQL_NAME", parameters...);
}
```

<b>Example: Default Mapping</b>

```java
default OperatorDefinition pow() {
	return function(DOUBLE, "POW", required(DOUBLE), required(DOUBLE));
}
```
This means : 

| Layer       | Value       |
| ----------- | ----------- |
| JQuery      | `pow(a, b)` |
| SQL         | `POW(a, b)` |
| Return type | `DOUBLE`    |

<b>Example: Custom Mapping (H2)</b>

Some databases (like H2) use POWER instead of POW.
```java
@Expose(identity="pow", description="Raises a numeric value to a specified power")
default OperatorDefinition pow() {
	return function(DOUBLE, "POWER", required(DOUBLE), required(DOUBLE));
}
```
| Layer | Value         |
| ----- | ------------- |
| JQuery| `pow(a, b)`   |
| SQL   | `POWER(a, b)` |

✅ After completing these steps, your Store is fully configured and ready to execute queries.