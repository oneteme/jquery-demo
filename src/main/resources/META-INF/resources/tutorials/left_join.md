This tutorial explains how to create and use a JOIN in your dataset.

1. Add the Join to Your DatasetCatalogue

First, define the join inside your table class by creating a method that returns a JoinsClause.

```java
// Orders.java

	default JoinsClause leftcustomer() {
		var cust = getInstance().getStore(DemoStore.class).customers();
		return joins(leftJoin(cust.getView(), customerId().eq(cust.id())));
	}

// Orders.java
```
A LEFT JOIN is created between the orders table and the customers table.

The join condition is:

```java
orders.customerId = customers.id
```

The join is named leftcustomer.
You are free to choose any name, but it should clearly describe the join.

2. Apply the Join in Your Query

Once the join is defined, you can apply it in your query using the join parameter.

```c#
join=leftcustomer
```

This will automatically apply the LEFT JOIN you defined earlier.

Tip: You can create multiple join methods in your table class and call them when needed in your queries.

And now your LEFT JOIN is up and ready and you can try it right now!
