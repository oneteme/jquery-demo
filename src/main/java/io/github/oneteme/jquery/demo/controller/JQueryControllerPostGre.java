package io.github.oneteme.jquery.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.usf.jquery.core.Definition;
import org.usf.jquery.mvc.MvcRequest;
import org.usf.jquery.mvc.QueryTemplate;
import org.usf.jquery.mvc.StoreManager;

import io.github.oneteme.jquery.demo.repo.stores.H2Store;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class JQueryControllerPostGre {

	@GetMapping("employees/h2")
	@QueryTemplate(store = H2Store.class, dataset = "employees", select = "id,lname,fname,start,photo,notes", view = "debug")
	public Object fetchEmployees(MvcRequest mvc) {
		return demoExecute(mvc);
	}

	@GetMapping("customers/h2")
	@QueryTemplate(store = H2Store.class, dataset = "customers", select = "id,name,contact,address,city,postal_code,country", view = "debug")
	public Object fetchCustomers(MvcRequest mvc, HttpServletResponse res) {
		return demoExecute(mvc);
	}

	@GetMapping("shippers/h2")
	@QueryTemplate(store = H2Store.class, dataset = "shippers", select = "id,name,phone", view = "debug")
	public Object fetchShippers(MvcRequest mvc) {
		return demoExecute(mvc);
	}

	@GetMapping("categories/h2")
	@QueryTemplate(store = H2Store.class, dataset = "categories", select = "id,name,description", view = "debug")
	public Object fetchCategories(MvcRequest mvc) {
		return demoExecute(mvc);
	}

	@GetMapping("suppliers/h2")
	@QueryTemplate(store = H2Store.class, dataset = "suppliers", select = "id,name,contact,address,city,postal_code,country,phone", view = "debug")
	public Object fetchSuppliers(MvcRequest mvc) {
		return demoExecute(mvc);
	}

	@GetMapping("orders/h2")
	@QueryTemplate(store = H2Store.class, dataset = "orders", select = "id,start,customer_id,employee_id,shipper_id", view = "debug")
	public Object fetchOrders(MvcRequest mvc) {
		return demoExecute(mvc);
	}

	@GetMapping("products/h2")
	@QueryTemplate(store = H2Store.class, dataset = "products", select = "id,name,supp_id,cat_id,price,unit", view = "debug")
	public Object fetchProducts(MvcRequest mvc) {
		return demoExecute(mvc);
	}

	@GetMapping("details/h2")
	@QueryTemplate(store = H2Store.class, dataset = "orders_details", select = "id,order_id,product_id,quantity", view = "debug")
	public Object fetchOrderDetails(MvcRequest mvc) {
		return demoExecute(mvc);
	}

	@GetMapping("dialect")
	public Object fetchOrderDetails(@RequestParam String name) {
		return StoreManager.getInstance().getDefaultStore().lookupDialect(name, Definition.class).invoke();
	}

	public Object demoExecute(MvcRequest mvc) {
		return demoExecute(mvc);
	}

	public Object demoExecute(MvcRequest mvc, HttpServletResponse res) {
		return mvc.execute(res);
	}

}
