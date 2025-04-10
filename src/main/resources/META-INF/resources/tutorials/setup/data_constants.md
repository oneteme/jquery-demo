1. Create DataConstants

Create a "DataConstants" class in which we will:
- Link our JQuery columns with actual column names from the database
- create join functions
- create criteria functions
- create operationColumns

2. Link JQuery columns to real columns

This is how we will link our JQuery columns with the columns of our database.

```java
// DataConstants.java

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataConstants {

	static String customerColumns(JQDemoColumn column) {
		return switch (column) {
		//**** case JQuery Column Name -> "Real column name" ****//
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
			//**** case JQuery Column Name -> "Real column name" ****//
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
		//... Shipper columns
		};
	}

	static String categoriesColumns(JQDemoColumn column) {
		return switch (column) {
		//... Categories columns
		};
	}

	//... DO THE SAME FOR THE REST OF THE TABLES
}

// DataConstants.java
```
