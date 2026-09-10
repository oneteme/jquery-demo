package org.usf.jquery.showcase.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.usf.jquery.core.KeyValueMapper;
import org.usf.jquery.core.Mappers;
import org.usf.jquery.mvc.MvcRequest;
import org.usf.jquery.mvc.QueryTemplate;
import org.usf.jquery.mvc.StoreManager;
import org.usf.jquery.showcase.erm.Employees;
import org.usf.jquery.showcase.erm.H2Store;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "db/h2")
@RequiredArgsConstructor
public class JQueryControllerH2 {
	
	@GetMapping("employees")
	@QueryTemplate(store = H2Store.class, dataset = "employees", select = "id,lname,fname,start,photo,notes", view = "debug")
	public Object fetchEmployees(MvcRequest mvc, HttpServletResponse res) {
		return demoExecute(mvc, res);
	}

	@GetMapping("customers")
	@QueryTemplate(store =  H2Store.class, dataset = "customers", select = "id,name,contact,address,city,postal_code,country", view = "debug")
	public Object fetchCustomers(MvcRequest mvc, HttpServletResponse res) {
		return demoExecute(mvc, res);
	}

	@GetMapping("shippers")
	@QueryTemplate(store =  H2Store.class, dataset = "shippers", select = "id,name,phone", view = "debug")
	public Object fetchShippers(MvcRequest mvc, HttpServletResponse res) {
		return demoExecute(mvc, res);
	}

	@GetMapping("categories")
	@QueryTemplate(store =  H2Store.class, dataset = "categories", select = "id,name,description", view = "debug")
	public Object fetchCategories(MvcRequest mvc, HttpServletResponse res) {
		return demoExecute(mvc, res);
	}

	@GetMapping("suppliers")
	@QueryTemplate(store =  H2Store.class, dataset = "suppliers", select = "id,name,contact,address,city,postal_code,country,phone", view = "debug")
	public Object fetchSuppliers(MvcRequest mvc, HttpServletResponse res) {
		return demoExecute(mvc, res);
	}

	@GetMapping("orders")
	@QueryTemplate(store =  H2Store.class, dataset = "orders", select = "id,start,customer_id,employee_id,shipper_id", view = "debug")
	public Object fetchOrders(MvcRequest mvc, HttpServletResponse res) {
		return demoExecute(mvc, res);
	}

	@GetMapping("products")
	@QueryTemplate(store =  H2Store.class, dataset = "products", select = "id,name,supp_id,cat_id,price,unit", view = "debug")
	public Object fetchProducts(MvcRequest mvc, HttpServletResponse res) {
		return demoExecute(mvc, res);
	}

	@GetMapping("details")
	@QueryTemplate(store =  H2Store.class, dataset = "orders_details", select = "id,order_id,product_id,quantity", view = "debug")
	public Object fetchOrderDetails(MvcRequest mvc, HttpServletResponse res) {
		return demoExecute(mvc, res);
	}

//	@GetMapping("dialect")
//	public Object fetchOrderDetails(@RequestParam String name) {
//		return StoreManager.getInstance().getDefaultStore().lookupDialect(name, Definition.class).invoke();
//	}

	public Object demoExecute(MvcRequest mvc) {
		return demoExecute(mvc, null);
	}

	public Object demoExecute(MvcRequest mvc, HttpServletResponse res) {
		return mvc.execute(res);
	}

}
