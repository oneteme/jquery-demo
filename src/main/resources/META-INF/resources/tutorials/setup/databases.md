1. Create DataConstants for the Database

Create a "DataConstants" class in which we will:

- Link our JQuery tables with actual table names from the database

2. Link JQuery tables to real tables

This is how we will link our JQuery tables with the tables of our database.

```java
// DBDataConstants.java

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataConstants {

	static String demoViews(ViewDecorator vd) {
		return switch ((JQDemoTable) vd) {
        //**** case JQuery Table Name -> "Real Table name" ****//
		case CUSTOMER: yield "CUSTOMERS_TABLE";
		case SHIPPER: yield "SHIPPERS_TABLE";
		case CATEGORY: yield "CATEGORIES_TABLE";
		case SUPPLIER: yield "SUPPLIERS_TABLE";
		case ORDER: yield "ORDERS_TABLE";
		case PRODUCT: yield "PRODUCTS_TABLE";
		case ORDER_DETAIL: yield "ORDERS_DETAILS_TABLE";
		case EMPLOYEE: yield "EMPLOYEES_TABLE";
		default: yield null;
		};
	}

}

// DBDataConstants.java
```

3. Create JQuery Database Enum

Now let's create our JQuery database Enum which will do the link between our databases and JQuery Databases and we add our databases.

```java
// JQDatabase.java

@RequiredArgsConstructor
public enum JQDatabase implements DatabaseDecorator {
    // DATABASE_NAME(The function in "DataConstants" that does the link between JQuery tables and real tables)
    DEMO(DBDataConstant::demoViews);
}

// JQDatabase.java
```

4. Setup JQuery Database

Then we add the constructor with 1 parameter : 
- private final Function<ViewDecorator, String> colMap;

```java
// JQDatabase.java

	private final Function<ViewDecorator, String> colMap;
	@Override
	public String identity() {
		return name().toLowerCase();
	}

	@Override
	public String viewName(ViewDecorator vd) {
		return colMap.apply(vd);
	}

// JQDatabase.java
```