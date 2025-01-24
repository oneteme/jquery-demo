1. Add the join to your table Interface

In the join function of your table you can add the join you need this way.

```java
// JQDemoTable.java

@Override
public JoinBuilder join(String name) {
	if (ORDER /* The table that contains the join */ == this && "leftcustomer".equals(name) /* The join name */) {
			return () -> new ViewJoin[] {
                 ViewJoin.leftJoin(JQDemoTable.CUSTOMER.view(), JQDemoTable.ORDER.column(JQDemoColumn.CUSTOMER_ID).eq(JQDemoTable.CUSTOMER.column(JQDemoColumn.ID))) 
                    };
		}
	return ViewDecorator.super.join(name);
}
```
In here we added a LEFT JOIN called "leftcustomer" (it's up to you to choose a name)

2. Apply the LEFT JOIN

Now to apply this "leftcustomer" join we created:

```c#
col1,col2,col3,...&join="leftcustomer"
```
