package io.github.oneteme.jquery.demo.controller;

import static org.usf.jquery.mvc.StoreManager.getInstance;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.usf.jquery.core.Definition;
import org.usf.jquery.mvc.MvcRequest;
import org.usf.jquery.mvc.QueryTemplate;
import org.usf.jquery.mvc.StoreCatalog;
import org.usf.jquery.mvc.StoreManager;

import io.github.oneteme.jquery.demo.repo.stores.DemoStore;
import io.github.oneteme.jquery.demo.repo.stores.H2Store;
import io.github.oneteme.jquery.demo.repo.stores.PostGreStore;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class JQueryControllerDialect {
	
	private static final Map<String, Class<? extends DemoStore>> DIALECTS = Map.of(
		    "h2", H2Store.class,
		    "postgresql", PostGreStore.class
		);
	
	@GetMapping("employees/{dialect}")
	@QueryTemplate(dataset= "employees", select= "id,lname,fname,start,photo,notes", view = "debug") 
	public Object fetchEmployees(MvcRequest mvc, @PathVariable String dialect) {
		return demoExecute(mvc, dialect);
	}

	@GetMapping("customers/{dialect}")
	@QueryTemplate(dataset= "customers", select= "id,name,contact,address,city,postal_code,country", view = "debug") 
	public Object fetchCustomers(MvcRequest mvc, @PathVariable String dialect, HttpServletResponse res) {
		return demoExecute(mvc, dialect);
	}

	@GetMapping("shippers/{dialect}")
	@QueryTemplate(dataset= "shippers", select= "id,name,phone", view = "debug") 
	public Object fetchShippers(MvcRequest mvc, @PathVariable String dialect) {
		return demoExecute(mvc, dialect);
	}

	@GetMapping("categories/{dialect}")
	@QueryTemplate(dataset= "categories", select= "id,name,description", view = "debug")
	public Object fetchCategories(MvcRequest mvc, @PathVariable String dialect) {
		return demoExecute(mvc, dialect);
	}

	@GetMapping("suppliers/{dialect}")
	@QueryTemplate(dataset= "suppliers", select= "id,name,contact,address,city,postal_code,country,phone", view = "debug") 
	public Object fetchSuppliers(MvcRequest mvc, @PathVariable String dialect) {
		return demoExecute(mvc);
	}

	@GetMapping("orders/{dialect}")
	@QueryTemplate(dataset= "orders", select= "id,start,customer_id,employee_id,shipper_id", view = "debug") 
	public Object fetchOrders(MvcRequest mvc, @PathVariable String dialect) {
		return demoExecute(mvc, dialect);
	}

	@GetMapping("products/{dialect}")
	@QueryTemplate(dataset= "products", select= "id,name,supp_id,cat_id,price,unit", view = "debug") 
	public Object fetchProducts(MvcRequest mvc, @PathVariable String dialect) {
		return demoExecute(mvc, dialect);
	}

	@GetMapping("details/{dialect}")
	@QueryTemplate(dataset= "orders_details", select= "id,order_id,product_id,quantity", view = "debug") 
	public Object fetchOrderDetails(MvcRequest mvc, @PathVariable String dialect) {
		return demoExecute(mvc, dialect);
	}

	@GetMapping("dialect")
	public Object fetchOrderDetails(@RequestParam String name) {
		return StoreManager.getInstance().getDefaultStore().lookupDialect(name, Definition.class).invoke();
	}
	
	public Object demoExecute(MvcRequest mvc) {
		return demoExecute(mvc, "h2");
	}
	
	public Object demoExecute(MvcRequest mvc, String dialect) {
		return demoExecute(mvc, dialect);
	}

	public Object demoExecute(MvcRequest mvc, String dialect, HttpServletResponse res) {
		mvc.getComposer().compose(getInstance().getStore(DIALECTS.get(dialect)));
		return mvc.execute(res);
	}
	
}
