package io.github.oneteme.jquery.demo.repo;

import org.usf.jquery.web.proxy.Bind;
import org.usf.jquery.web.proxy.StoreResource;

public interface DemoStore extends StoreResource {

	@Bind("CUSTOMERS_TABLE")
	Customers customers();

	@Bind("SHIPPERS_TABLE")
	Shippers shippers();

	@Bind("CATEGORIES_TABLE")
	Categories categories();

	@Bind("SUPPLIERS_TABLE")
	Suppliers suppliers();

	@Bind("ORDERS_TABLE")
	Orders orders();

	@Bind("PRODUCTS_TABLE")
	Products products();

	@Bind("ORDERS_DETAILS_TABLE")
	OrdersDetails orders_details();

	@Bind("EMPLOYEES_TABLE")
	Employees employees();
}
