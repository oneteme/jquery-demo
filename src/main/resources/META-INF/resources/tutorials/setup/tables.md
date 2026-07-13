This guide explains how to configure a Store in JQuery.

A Store acts as the entry point of your API, grouping:

- all datasets (tables / views)
- custom functions
- controlling what is exposed to the API

The setup consists of three main steps:

- Create a Store interface
- Bind datasets (views)
- Define or override functions

1. Create a Store Interface

For each database, create an interface that extends StoreResource.

 ```java
// DemoStore.java

public interface DemoStore extends StoreResource {

}
 ```
This interface acts as the root container of your query system.

All datasets and custom functions will be defined here.

2. Bind Datasets (Views)

Inside the store, declare all datasets you want to expose.

Each dataset:

- represents a table or view
- must be linked using @Bind
- returns a previously defined DatasetCatalogue

Basic Syntax : 
 ```java 
 //Sample
@Bind("REAL_TABLE_NAME")
MyDataset datasetName();
 ```

| Element           | Description                     |
| ----------------- | ------------------------------- |
| `REAL_TABLE_NAME` | Table/view name in the database |
| `datasetName()`   | Name used in Java and queries   |

Example:
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

3. Customize Dataset Exposure

Like columns, datasets can be customized using @Expose.

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

4. Function Mapping

The Store defines how JQuery functions are translated into SQL.

You are not creating or overriding SQL functions.

Instead, you define how a function used in a query is converted into SQL.

Basic Syntax
```java
default OperatorDefinition functionName() {
	return function(RETURN_TYPE, "SQL_NAME", parameters...);
}
```

Example: Default Mapping
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

Example: Custom Mapping (H2)

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