1. Add the joins to your table Interface

In the join function of your table you can add the joins you need this way.

```java
// JQDemoTable.java

@Override
public JoinBuilder join(String name) {
	if (ORDER /* The table that contains the join */ == this && "innercustomer".equals(name) /* The join name */) {
		return () -> new ViewJoin[] {
             ViewJoin.innerJoin(JQDemoTable.CUSTOMER.view(), JQDemoTable.ORDER.column(JQDemoColumn.CUSTOMER_ID).eq(JQDemoTable.CUSTOMER.column(JQDemoColumn.ID)))
                 };
	}
	if (ORDER /* The table that contains the join */ == this && "leftcustomer".equals(name) /* The join name */) {
		return () -> new ViewJoin[] {
             ViewJoin.leftJoin(JQDemoTable.CUSTOMER.view(), JQDemoTable.ORDER.column(JQDemoColumn.CUSTOMER_ID).eq(JQDemoTable.CUSTOMER.column(JQDemoColumn.ID))) 
                };
	}
	if (ORDER /* The table that contains the join */ == this && "rightcustomer".equals(name) /* The join name */) {
		return () -> new ViewJoin[] {
             ViewJoin.rightJoin(JQDemoTable.CUSTOMER.view(), JQDemoTable.ORDER.column(JQDemoColumn.CUSTOMER_ID).eq(JQDemoTable.CUSTOMER.column(JQDemoColumn.ID))) 
                };
	}
	return ViewDecorator.super.join(name);
}
```
In here we added a INNER JOIN called "innercustomer" (it's up to you to choose a name)

2. Apply the INNER JOIN

Now to apply all the joins we created:

```c#
col1,col2,col3,...&join="innercustomer,leftcustomer,rightcustomer"
```


            // {
			// 	"title": "MULTIPLE JOINS",
			// 	"view": "orders",
			// 	"filter": "join=\"innercustomer,leftcustomer,rightcustomer\"",
			// 	"tutorial":"multiple_join.md"
			// }
