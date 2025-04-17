1. Create Controller

And now for the final step we are gonna need a controller for our queries.
So let's start by creating the controller

```java
// JQueryController.java

@RestController
@RequiredArgsConstructor
public class JQueryController {
	private final DataSource ds;
}

// JQueryController.java
```

2. Add mappings

And then we add the routes to our different tables in this way :

```java
// JQueryController.java

@GetMapping("route")
public Map<String, Object> myMappingFunction(
	@RequestQueryParam(database = "database name",view = "table name", defaultColumns = "col1,col2,...,avg,count,...") QueryBuilder query) {
		try {
			return query.build().execute(ds);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

// JQueryController.java
```

So in our case a mapping function for the customers table would look like this:

```java
// JQueryController.java

@GetMapping("customers")
	public Map<String, Object> fetchCustomers(
			@RequestQueryParam(database = "demo",view = "customer", defaultColumns = "id,customer,contact,address,city,postal_code,country") QueryBuilder query) {
		try {
			return query.build().execute(ds);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

//... DO THE SAME FOR ALL OTHER TABLES

// JQueryController.java
```
