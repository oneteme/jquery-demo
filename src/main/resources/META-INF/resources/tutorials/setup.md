1. Create columns

Lets create our columns interface which we will call "JQDemoColumns"
 ```java
//JQDemoColumn.java

@RequiredArgsConstructor
public enum JQDemoColumn implements ColumnDecorator {

}

//JQDemoColumn.java
 ```
Then we add the columns to the interface

 ```java
//JQDemoColumn.java

@RequiredArgsConstructor
public enum JQDemoColumn implements ColumnDecorator {
	ID("id"),
	NAME("name"),
	START("start"),
	FILTER("filter"),
	TABLE_NAME("table_name"),
	CUSTOMER("customer"),
	CONTACT("contact"),
	ADDRESS("address"),
	CITY("city"),
	POSTAL_CODE("postal_code"),
	COUNTRY("country"),
	LAST_NAME("last_name"),
	PHOTO("photo"),
	NOTES("notes"),
	DESCRIPTION("description"),
	PHONE("phone"),
	UNIT("unit"),
	PRICE("price"),
	QUANTITY("quantity"),
	CUSTOMER_ID("customer_id"),
	EMPLOYEE_ID("employee_id"),
	SHIPPER_ID("shipper_id"),
	SUPPLIER_ID("supplier_id"),
	CATEGORY_ID("category_id"),
	PRODUCT_ID("category_id"),
	ORDER_ID("order_id"),
	DEFINITION("definition"),
	SYNTAX("syntax"),
	;
}

//JQDemoColumn.java
 ```

Then we add the constructor with 3 parameters : 
- reference : String
- builder : ColumnBuilder
- crBuilder : CriteriaBuilder<ComparisonExpression> (optional : see Criteria tutorial later)

 ```java
//JQDemoColumn.java

@RequiredArgsConstructor
public enum JQDemoColumn implements ColumnDecorator {
//... columns
;
    private final String reference;
	private final ColumnBuilder builder;
	private final CriteriaBuilder<ComparisonExpression> crBulder;
	
	JQDemoColumn(@NonNull String ref)
	{
		this(ref, null, null);
	}
	
	JQDemoColumn(@NonNull String ref, @NonNull ColumnBuilder builder) {
        this(ref, builder, null);
    }

	@Override
	public String identity() {
		return this.name().toLowerCase();
	}

	@Override
	public String reference(ViewDecorator vd) {
		return reference;
	}

	@Override
	public ColumnBuilder builder(ViewDecorator vd) {
		return Objects.nonNull(builder) ? builder : ColumnDecorator.super.builder(vd);
	}

	@Override
	public CriteriaBuilder<ComparisonExpression> criteria(String name) {
		return "ym".equals(name) && Objects.nonNull(crBulder) ? crBulder : null;
	}
}

//JQDemoColumn.java
 ```
2. Create DataConstants

Create a "DataConstants" class in which we will:
- Link our JQuery columns with actual column names from the database
- Add join functions
- Add criteria functions
- Add operationColumns

This is how we will link our JQuery columns with the columns of our database.
```java
// DataConstants.java

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataConstants {

	static String customerColumns(JQDemoColumn column) {
		return switch (column) {
		case ID -> "CUSTOMER_ID";
		case CUSTOMER -> "CUSTOMER_NAME";
		case CONTACT -> "CONTACT_NAME";
		case ADDRESS -> "ADDRESS";
		case CITY -> "CITY";
		case POSTAL_CODE -> "POSTAL_CODE";
		case COUNTRY -> "COUNTRY";
		default -> null;
		};
	}

	static String employeesColumns(JQDemoColumn column) {
		return switch (column) {
		case ID -> "EMPLOYEE_ID";
		case LAST_NAME -> "LAST_NAME";
		case NAME -> "FIRST_NAME";
		case START -> "BIRTH_DATE";
		case PHOTO -> "PHOTO";
		case NOTES -> "NOTES";
		default -> null;
		};
	}

	static String shippersColumns(JQDemoColumn column) {
		return switch (column) {
		case ID -> "SHIPPER_ID";
		case NAME -> "SHIPPER_NAME";
		case PHONE -> "PHONE";
		default -> null;
		};
	}

	static String categoriesColumns(JQDemoColumn column) {
		return switch (column) {
		case ID -> "CATEGORY_ID";
		case NAME -> "CATEGORY_NAME";
		case DESCRIPTION -> "DESCRIPTION";
		default -> null;
		};
	}

	static String suppliersColumns(JQDemoColumn column) {
		return switch (column) {
		case ID -> "SUPPLIER_ID";
		case NAME -> "SUPPLIER_NAME";
		case CONTACT -> "CONTACT_NAME";
		case ADDRESS -> "ADDRESS";
		case CITY -> "CITY";
		case POSTAL_CODE -> "POSTAL_CODE";
		case COUNTRY -> "COUNTRY";
		case PHONE -> "PHONE";
		default -> null;
		};
	}

	static String ordersColumns(JQDemoColumn column) {
		return switch (column) {
		case ID -> "ORDER_ID";
		case START -> "ORDER_DATE";
		case CUSTOMER_ID -> "CUSTOMER_ID";
		case EMPLOYEE_ID -> "EMPLOYEE_ID";
		case SHIPPER_ID -> "SHIPPER_ID";
		default -> null;
		};
	}

	static String productsColumns(JQDemoColumn column) {
		return switch (column) {
		case ID -> "PRODUCT_ID";
		case NAME -> "PRODUCT_NAME";
		case SUPPLIER_ID -> "SUPPLIER_ID";
		case CATEGORY_ID -> "CATEGORY_ID";
		case PRICE -> "PRICE";
		case UNIT -> "UNIT";
		default -> null;
		};
	}

	static String ordersDetailsColumns(JQDemoColumn column) {
		return switch (column) {
		case ID -> "ORDER_DETAIL_ID";
		case ORDER_ID -> "ORDER_ID";
		case PRODUCT_ID -> "PRODUCT_ID";
		case QUANTITY -> "QUANTITY";
		default -> null;
		};
	}
}

// DataConstants.java
```
3. Create Table

Now let's create our tables interface which we will call "JQDemoTable"
```java
// JQDemoTable.java

@RequiredArgsConstructor
public enum JQDemoTable implements ViewDecorator {

}

// JQDemoTable.java
```
We add the tables to the interface and we link the tables to their columns using the functions we created in "DataConstants"

```java
// JQDemoTable.java

@RequiredArgsConstructor
public enum JQDemoTable implements ViewDecorator {
    CUSTOMER(DataConstants::customerColumns),
	SHIPPER(DataConstants::shippersColumns),
	CATEGORY(DataConstants::categoriesColumns),
	SUPPLIER(DataConstants::suppliersColumns),
	ORDER(DataConstants::ordersColumns,DataConstants::orderJoin),
	PRODUCT(DataConstants::productsColumns),
	ORDER_DETAIL(DataConstants::ordersDetailsColumns),
	EMPLOYEE(DataConstants::employeesColumns),
	;
}

// JQDemoTable.java
```
Then we add the constructor with 2 parameters :
- colMap : Function<JQDemoColumn, String>
- joins : Function<String, JoinBuilder> (optional : see Join tutorial later)

```java
// JQDemoTable.java

@RequiredArgsConstructor
public enum JQDemoTable implements ViewDecorator {
    // Tables...
	;
    private final Function<JQDemoColumn, String> colMap;
	private final Function<String, JoinBuilder> joins;
	
	private JQDemoTable(Function<JQDemoColumn, String> colMap) {
		this.colMap = colMap;
		this.joins = null;
	}
	
	@Override
	public String identity() {
		return name().toLowerCase();
	}

	@Override
	public CriteriaBuilder<DBFilter> criteria(String name) { 
		return ViewDecorator.super.criteria(name);
	}

	@Override
	public String columnName(ColumnDecorator cd) {
		return colMap.apply((JQDemoColumn) cd);
	}
	@Override
	public JoinBuilder join(String name) {
		return joins == null ? ViewDecorator.super.join(name) : joins.apply(name);
	}
}

// JQDemoTable.java
```

4. Create Database

Now that we have our Tables and Columns we can setup our database.

Yes you read that right, you can have more than one database with JQuery.

So let's get started by creating an interface for our database and let's call it "JQDatabase".

```java
// JQDatabase.java

@RequiredArgsConstructor
public enum JQDatabase implements DatabaseDecorator {

}

// JQDatabase.java
```

Before going any further on our Database interface we need to create a DataConstants class for the database.

In this class we are gonna link the JQuery names of our tables with their actual names from the actual database. 

We are gonna create an interface which we will call "DBDataConstants".

```java
// DBDataConstants.java

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class DBDataConstant {
	
	static String demoViews(ViewDecorator vd) {
		return switch ((JQDemoTable) vd) {
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

In our case now we have only one database but it is possible to have more than one.

```java
// JQDatabase.java

@RequiredArgsConstructor
public enum JQDatabase implements DatabaseDecorator {

}

// JQDatabase.java
```
5. WebMvcConfig
6. Controller