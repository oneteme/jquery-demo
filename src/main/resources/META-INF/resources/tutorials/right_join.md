1. Add the join to your table Enum

In the join function of your table you can add the join you need this way.

```java
// JQDemoTable.java

@Override
public JoinBuilder join(String name) {
	if (ORDER /* The table that contains the join */ == this && "rightcustomer".equals(name) /* The join name */) {
			return () -> new ViewJoin[] {
                 ViewJoin.rightJoin(JQDemoTable.CUSTOMER.view(), JQDemoTable.ORDER.column(JQDemoColumn.CUSTOMER_ID).eq(JQDemoTable.CUSTOMER.column(JQDemoColumn.ID)))
                    };
		}
	return ViewDecorator.super.join(name);
}
```
In here we added a RIGHT JOIN called "rightcustomer" (it's up to you to choose a name)

2. Apply the RIGHT JOIN

Now to apply this "rightcustomer" join we created:

```c#
col1,col2,col3,...&join="rightcustomer"
```
