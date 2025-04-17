1. Create Table

Let's create our tables ENUM which is a "ViewDecorator" and we will call it "JQDemoTable"
```java
// JQDemoTable.java

@RequiredArgsConstructor
public enum JQDemoTable implements ViewDecorator {

}

// JQDemoTable.java
```
We add the tables to the enum and we link the tables to their columns using the functions we created in "DataConstants"

```java
// JQDemoTable.java

@RequiredArgsConstructor
public enum JQDemoTable implements ViewDecorator {
    CUSTOMER(DataConstants::customerColumns),
	SHIPPER(DataConstants::shippersColumns),
	CATEGORY(DataConstants::categoriesColumns),
	// ... tables
	;
}

// JQDemoTable.java
```

2. Setup Tables constructor

Then we add the constructor with 2 parameters :
- colMap : Function<JQDemoColumn, String>
- joins : Function<String, JoinBuilder> (OPTIONAL : see Join tutorial later)

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