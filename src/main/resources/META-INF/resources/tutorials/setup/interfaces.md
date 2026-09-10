### Catalog interfaces

This guide explains the interfaces you need to create in order to start using `JQuery`.

The setup consists of two steps:

- Create a Store Catalog (Database)
- Create a Dataset Catalog (table/view)

1. Create a Store Interface

For each `database`, create an interface that extends `StoreResource`.

 ```java
// DemoStore.java
public interface DemoStore extends StoreResource {

}
 ```
This interface acts as the root container of your query system.

All datasets and custom functions will be defined here.

2. Create Dataset Interfaces

For each `table` or `view` in your database, you must create a `DatasetCatalogue` interface.

This interface represents the dataset that will be exposed through the API.

Example: if you have a table called `CUSTOMERS_TABLE`, create a corresponding interface.

 ```java
//Customers.java
public interface Customers extends DatasetCatalogue<DemoStore> {

}
 ```
This interface will later contain all the columns that can be queried from this dataset.

✅ After completing these steps, you will need to bind your catalogs with your database resources.