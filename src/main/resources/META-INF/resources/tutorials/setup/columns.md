### Setup a Dataset Catalog

This guide explains how to configure JQuery columns.

The setup consists of two main steps:

- Declare and bind columns
- Configure column exposure and metadata

For this guide we will be using the previously created **DatasetCatalog** called **Customers**

```java
//Customers.java
public interface Customers extends DatasetCatalogue<DemoStore> {

}
```

1. Add the columns

Inside the dataset interface, define the columns that can be queried.

Each column must:

- Return a `ViewColumn`
- Be linked to the actual database column name using `@Bind`
- **Optional :** Define the its type manually if needed using `@Typed`

<b>Basic Syntax </b> 

 ```java 
	// Sample
	@Bind("REAL COLUMN NAME")
	@Typed(JDBCType.myType)
	ViewColumn jquery_column_name();
 ```

| Element              | Description                      |
| -------------------- | -------------------------------- |
| `REAL_COLUMN_NAME`   | Column name in the database      |
| `JDBCType`   		   | Column type in the database      |
| `jqueryColumnName()` | Column identifier used in Java   |


<b>Example</b>

 ```java
//Customers.java
public interface Customers extends DatasetCatalogue {

	@Bind("CUSTOMER_ID")
	@Typed(JDBCType.UUID)
	ViewColumn id();
	
	@Bind("CUSTOMER_NAME")
	@Typed(JDBCType.VARCHAR)
	ViewColumn name();
	
	@Bind("CONTACT_NAME")
	ViewColumn contact();
	
	@Bind("ADDRESS")
	ViewColumn address();
	
	// other columns
	
	//create partition, join, criteria
}
 ```

| JQuery Column | Database Column | Type 				  					  |
| ------------- | --------------- | ----------------------------------------- |
| `id()`        | CUSTOMER_ID     | UUID 				  					  |
| `name()`      | CUSTOMER_NAME   | VARCHAR 			  					  |
| `contact()`   | CONTACT_NAME    | VARCHAR **(default : defined by JQuery)** |
| `address()`   | ADDRESS         | VARCHAR **(default : defined by JQuery)** |

These columns can now be referenced in `JQuery` queries.

2. Columns refactor

Columns can be customized using the `@Expose` annotation.

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

```scss
/customers?select=customer_address
```

```java
Customers.address();
```

✅ After completing these steps, the dataset is ready to be used with JQuery query syntax.