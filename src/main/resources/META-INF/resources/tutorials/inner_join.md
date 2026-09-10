This tutorial explains how to create and use an INNER JOIN in your dataset.

1. Add the Join to Your DatasetCatalogue

First, define the join inside your table class by creating a method that returns a JoinsClause.

```java
// Orders.java

	default JoinsClause innerCustomer() {
		var cust = getInstance().getStore(DemoStore.class).customers();
		return joins(rightJoin(cust.getView(), customerId().eq(cust.id())));
	}

// Orders.java
```
A INNER JOIN is created between the orders table and the customers table.

The join condition is:

```javascript
orders.customerId = customers.id
```

The join is named innerCustomer.
You are free to choose any name, but it should clearly describe the join.

2. Apply the Join in Your Query

Once the join is defined, you can apply it in your query using the join parameter.

```c#
join=innerCustomer
```

This will automatically apply the INNER JOIN you defined earlier.

Tip: You can create multiple join methods in your table class and call them when needed in your queries.

And now your INNER JOIN is up and ready and you can try it right now!

