1. Create columns enum

Lets create our columns ENUM which is a "ColumnDecorator" and will call it "JQDemoColumn"
 ```java
//JQDemoColumn.java

@RequiredArgsConstructor
public enum JQDemoColumn implements ColumnDecorator {

}

//JQDemoColumn.java
 ```

2. Add the columns to the enum

In this enum we are going to add the JQuery columns and these column names are gonna be used on our queries instead of the real column names

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
	//... Other columns
	;
}

//JQDemoColumn.java
 ```
3. Setup columns constructor

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
