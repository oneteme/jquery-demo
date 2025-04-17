1. Add the join to your table Enum

Add to your constructor a joins of type Function<String, JoinBuilder> and then add the join function
```java
// JQDemoTable.java

private final Function<String, JoinBuilder> joins;
@Override
public JoinBuilder join(String name) {
	return joins == null ? ViewDecorator.super.join(name) : joins.apply(name);
}
```
2. Create the join function

Now to add the innerJoin that we want to apply we create a function that returns a "ViewJoin" and it may take a View as a parameter("JQDemoTable" in our case)

```java
// DataConstants.java

// Without View Parameter
public static ViewJoin joinOrderCustomer() {
	return innerJoin(JQDemoTable.CUSTOMER.view(),JQDemoTable.ORDER.column(JQDemoColumn.CUSTOMER_ID).eq(JQDemoTable.CUSTOMER.column(JQDemoColumn.ID)));
}
```

When the same join function will be used for 2 different tables we can add a View as Parameter ("JQDemoTable" in our case).

For example this join function may be used in "Orders" as well as "Customers" table so we create a dynamic function.

```java
// DataConstants.java

// With View Parameter
public static ViewJoin joinOrderCustomer(JQDemoTable view) {
	return ViewJoin.innerJoin(view.view(),JQDemoTable.ORDER.column(JQDemoColumn.CUSTOMER_ID).eq(JQDemoTable.CUSTOMER.column(JQDemoColumn.ID)));
}
```

3. Prepare the joins for your table 

Now let's say we want to add a join to our "Orders" table so we create a function called orderJoin that takes a "String" as parameter and returns "JoinBuilder".

In this function we prepare all of the joins that will be applied with "Orders" table

```java
// DataConstants.java
public static JoinBuilder orderJoin(String name) {
	return switch (name) {
        //... other joins
	    case "innercustomer" -> () -> new ViewJoin[] { joinOrderCustomer(JQDemoTable.CUSTOMER) };
	    default -> null;
	};
}
```
4. Add the join function to the view

```java
// JQDemoTable.java

ORDER(DataConstants::ordersColumns,DataConstants::orderJoin)
```

5. Full code

```java
// JQDemoTable.java

@RequiredArgsConstructor
public enum JQDemoTable implements ViewDecorator {
    //... other tables
	ORDER(DataConstants::ordersColumns,DataConstants::orderJoin),
	;

	private final Function<JQDemoColumn, String> colMap;
	private final Function<String, JoinBuilder> joins;
	
	private JQDemoTable(Function<JQDemoColumn, String> colMap) {
		this.colMap = colMap;
		this.joins = null;
	}

    // JOIN
	@Override
	public JoinBuilder join(String name) {
		return joins == null ? ViewDecorator.super.join(name) : joins.apply(name);
	}
}
```

```java
// DataConstants.java
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataConstants {
	public static JoinBuilder orderJoin(String name) {
		return switch (name) {
        //... other joins
		case "innercustomer" -> () -> new ViewJoin[] { joinOrderCustomer(JQDemoTable.CUSTOMER) };
		default -> null;
		};
	}

	public static ViewJoin joinOrderCustomer(JQDemoTable view) {
		return innerJoin(view.view(),JQDemoTable.ORDER.column(JQDemoColumn.CUSTOMER_ID).eq(JQDemoTable.CUSTOMER.column(JQDemoColumn.ID)));
	}

}
```

6. Apply the join

Now to apply this "innercustomer" join we created:
```c#
col1,col2,col3,...&join="innercustomer"
```
