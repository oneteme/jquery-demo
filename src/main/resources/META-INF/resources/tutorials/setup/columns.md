This guide explains how to configure JQuery columns.

The setup consists of three main steps:

- Create a Dataset interface
- Declare and bind columns
- Configure column exposure and metadata

1. Create Dataset Interfaces

For each table or view in your database, you must create a DatasetResource interface.

This interface represents the dataset that will be exposed through the API.

Example: if you have a database table called CUSTOMERS_TABLE, create a corresponding interface.
 ```java
//Customers.java

public interface Customers extends DatasetResource {

}

//Customers.java
 ```
This interface will later contain all the columns that can be queried from this dataset.

2. Add the columns

Inside the dataset interface, define the columns that can be queried.

Each column must:

- Return a ViewColumn
- Be linked to the actual database column name using @Bind

Basic Syntax : 
 ```java 
	// Sample
	@Bind("REAL COLUMN NAME")
	ViewColumn jquery_column_name();
 ```

| Element              | Description                      |
| -------------------- | -------------------------------- |
| `REAL_COLUMN_NAME`   | Column name in the database      |
| `jqueryColumnName()` | Column identifier used in Java   |


Example:
 ```java
//Customers.java

public interface Customers extends DatasetResource {

	@Bind("CUSTOMER_ID")
	ViewColumn id();
	
	@Bind("CUSTOMER_NAME")
	ViewColumn name();
	
	@Bind("CONTACT_NAME")
	ViewColumn contact();
	
	@Bind("ADDRESS")
	ViewColumn address();
	
	// other columns
	
	//create partition, join, criteria
}

//Customers.java
 ```
| JQuery Column | Database Column |
| ------------- | --------------- |
| `id()`        | CUSTOMER_ID     |
| `name()`      | CUSTOMER_NAME   |
| `contact()`   | CONTACT_NAME    |
| `address()`   | ADDRESS         |

These columns can now be referenced in JQuery queries.

3. Columns refactor

Columns can be customized using the @Expose annotation.

This annotation allows you to:

- rename the column for API queries
- add documentation
- hide columns from public usage

| Parameter     | Description                            |
| ------------- | -------------------------------------- |
| `identity`    | Name used in web queries               |
| `description` | Short description of the column        |
| `false`       | Prevents the column from being exposed |

 ```java
//Customers.java

	@Expose(identity="customer_address", description="Customer's home address")
	@Bind("ADDRESS")
	ViewColumn address();

	// we can't use this column because it is not exposed
	@Expose(false)
	@Bind("CONTACT_NAME")
	ViewColumn contact();


//Customers.java
 ```

 | Database Column | Exposed | JQuery Column     |
| --------------- | ------- | ------------------ |
| `ADDRESS`       | Yes     | `customer_address` |
| `CONTACT_NAME`  | No      | Not accessible     |

Example of using the customised columns:

in API : 
http://localhost:8080/customers?field=customer_address

in Java : 
```java
Customers.address();
```

✅ After completing these steps, the dataset is ready to be used with JQuery query syntax.