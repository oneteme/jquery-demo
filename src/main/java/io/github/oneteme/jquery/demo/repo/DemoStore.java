package io.github.oneteme.jquery.demo.repo;

import static org.usf.jquery.core.JDBCType.DOUBLE;
import static org.usf.jquery.core.Operators.constant;
import static org.usf.jquery.core.Operators.function;
import static org.usf.jquery.core.Parameter.required;

import org.usf.jquery.core.Column;
import org.usf.jquery.core.Dialect;
import org.usf.jquery.core.OperatorDefinition;
import org.usf.jquery.core.Predicate;
import org.usf.jquery.web.proxy.Bind;
import org.usf.jquery.web.proxy.Expose;
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

	@Expose(identity="orders_details", description="")
	@Bind("ORDERS_DETAILS_TABLE")
	OrdersDetails ordersDetails();

	@Bind("EMPLOYEES_TABLE")
	Employees employees();
	
	@Expose(identity="pow", description="Raises a numeric value to a specified power")
	default OperatorDefinition pow() {
		return function(DOUBLE, "POWER", required(DOUBLE), required(DOUBLE));
	}
	
	@Expose(identity="pi", description="")
	default OperatorDefinition pi() {
		return constant(DOUBLE, "PI()");
	}
	
	@Expose(identity="random", description="")
	default OperatorDefinition random() {
		return constant(DOUBLE, "RANDOM()");
	}
	
	default Column getPi() {
		return Dialect.getDialect().pi().invoke();
	}
	
//	default Predicate predDemo() {
//		return null;
//		
//	}
}
