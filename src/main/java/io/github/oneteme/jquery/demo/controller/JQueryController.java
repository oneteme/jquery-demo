package io.github.oneteme.jquery.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.usf.jquery.mvc.MvcRequest;
import org.usf.jquery.mvc.QueryTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class JQueryController {

	@GetMapping("employees")
	@QueryTemplate(dataset= "employees", select= "id,lname,fname,start,photo,notes", view = "debug") 
	public Object fetchEmployees(MvcRequest mvc) {
		return mvc.execute();
	}

	@GetMapping("customers")
	@QueryTemplate(dataset= "customers", select= "id,name,contact,address,city,postal_code,country", view = "debug") 
	public Object fetchCustomers(MvcRequest mvc) {
		return mvc.execute();
	}

	@GetMapping("shippers")
	@QueryTemplate(dataset= "shippers", select= "id,name,phone", view = "debug") 
	public Object fetchShippers(MvcRequest mvc) {
		return mvc.execute();
	}

	@GetMapping("categories")
	@QueryTemplate(dataset= "categories", select= "id,name,description", view = "debug")
	public Object fetchCategories(MvcRequest mvc) {
		return mvc.execute();
	}

	@GetMapping("suppliers")
	@QueryTemplate(dataset= "suppliers", select= "id,name,contact,address,city,postal_code,country,phone", view = "debug") 
	public Object fetchSuppliers(MvcRequest mvc) {
		return mvc.execute();
	}

	@GetMapping("orders")
	@QueryTemplate(dataset= "orders", select= "id,start,customer_id,employee_id,shipper_id", view = "debug") 
	public Object fetchOrders(MvcRequest mvc) {
		return mvc.execute();
	}

	@GetMapping("products")
	@QueryTemplate(dataset= "products", select= "id,name,supp_id,cat_id,price,unit", view = "debug") 
	public Object fetchProducts(MvcRequest mvc) {
		return mvc.execute();
	}

	@GetMapping("details")
	@QueryTemplate(dataset= "orders_details", select= "id,order_id,product_id,quantity", view = "debug") 
	public Object fetchOrderDetails(MvcRequest mvc) {
		return mvc.execute();
	}
}
