package io.github.oneteme.jquery.demo.controller;

import static io.github.oneteme.jquery.demo.JQDatabase.DEMO;
import static org.usf.jquery.core.Mappers.keyValueMapper;
import static org.usf.jquery.web.Keyword.COLUMN;
import static org.usf.jquery.web.Keyword.ORDER;
import static org.usf.jquery.web.proxy.StoreManager.getInstance;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.usf.jquery.core.Environment.SimpleEnvironment;
import org.usf.jquery.core.Query;
import org.usf.jquery.core.QueryComposer;
import org.usf.jquery.web.QueryRequestFilter;
import org.usf.jquery.web.proxy.MvcRequest;
import org.usf.jquery.web.proxy.QueryRequest;
import org.usf.jquery.web.proxy.StoreResource;

import io.github.oneteme.jquery.demo.repo.DemoStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class JQueryController {

	static SimpleEnvironment NO_ENV = new SimpleEnvironment(null, null, null);
	
	@GetMapping("employees")
	@QueryRequest(dataset= "employees", fields= "id,lname,fname,start,photo,notes") 
	public Object fetchEmployees(MvcRequest mvc) {
		return execute(mvc);
	}

	@GetMapping("customers")
	@QueryRequest(dataset= "customers", fields = "id,name,contact,address,city,postal_code,country") 
	public Object fetchCustomers(MvcRequest mvc) {
		return execute(mvc);
	}

	@GetMapping("shippers")
	@QueryRequest(dataset= "shippers", fields = "id,name,phone") 
	public Object fetchShippers(MvcRequest mvc) {
		return execute(mvc);
	}

	@GetMapping("categories")
	@QueryRequest(dataset= "categories", fields = "id,name,description")
	public Object fetchCategories(MvcRequest mvc) {
		return execute(mvc);
	}

	@GetMapping("suppliers")
	@QueryRequest(dataset= "suppliers", fields = "id,name,contact,address,city,postal_code,country,phone") 
	public Object fetchSuppliers(MvcRequest mvc) {
		return execute(mvc);
	}

	@GetMapping("orders")
	@QueryRequest(dataset= "orders", fields = "id,start,customer_id,employee_id,shipper_id") 
	public Object fetchOrders(MvcRequest mvc) {
		return execute(mvc);
	}

	@GetMapping("products")
	@QueryRequest(dataset= "products", fields = "id,name,supp_id,cat_id,price,unit") 
	public Object fetchProducts(MvcRequest mvc) {
		return execute(mvc);
	}

	@GetMapping("details")
	@QueryRequest(dataset= "orders_details", fields = "id,order_id,product_id,quantity") 
	public Object fetchOrderDetails(MvcRequest mvc) {
		return execute(mvc);
	}

	// RequestQueryParamWithCheck
	@GetMapping("products/test")
	public Map<String, Object> fetchProductsWithCheck(
			@QueryRequestFilter(database = "demo",view = "product", column = "id,name,price",
			filters= {"price=10,20,30,40,=50"},order="price") QueryComposer query) {
		return usingSpringJdbc(query);
	}
	
	@GetMapping("customers/test")
	public Map<String, Object> fetchCustomersWithCheck(
			@QueryRequestFilter(database = "demo",view = "customer", column = "id,customer,country", order = "country.desc", limit = 5, offset = 5,filters={"=country.notNull"}) QueryComposer query) {
		return usingSpringJdbc(query);
	}
	
	@GetMapping("customers/distinct/test")
	public Map<String, Object> fetchCustomersDistinctWithCheck(
			@QueryRequestFilter(database = "demo", view = "customer", column = "id,customer,country", distinct = true, order = "country.desc", limit = 5, offset = 5) QueryComposer query) {
		return usingSpringJdbc(query);
	}
	
	@GetMapping("customers/allow/test")
	public Map<String, Object> fetchCustomersAllowColWithCheck(
			@QueryRequestFilter(database = "demo", view = "customer", column = "id,customer,country", mergeParameters = {COLUMN,ORDER}, distinct = true, order = "country.desc", limit = 5, offset = 5) QueryComposer query) {
		return usingSpringJdbc(query);
	}

	@GetMapping("orders/distinct/test")
	public Map<String, Object> fetchOrdersWithCheck(
			@QueryRequestFilter(database = "demo", view = "order", column = "id,start,customer_id,employee_id,shipper_id", distinct = true, join = "innercustomer", limit = 5) QueryComposer query) {
		return usingSpringJdbc(query);
	}
	
	@GetMapping("orders/test")
	public Map<String, Object> fetchDistinctOrdersWithCheck(
			@QueryRequestFilter(database = "demo",view = "order", column = "id,start,customer_id,employee_id,shipper_id", join = "innercustomer", limit = 5) QueryComposer query) {
		return usingSpringJdbc(query);
	}
	
	@Deprecated
	private Map<String, Object> usingSpringJdbc(QueryComposer req) {
		Map<String, Object> result = new HashMap<>();
		try {			
			var query = DEMO.execute(req);
//			var sqlQuery = req.compose(null).build().sql();
			result.put("query", "");
			result.put("result", query);
		} catch (Exception e) {
			result.put("test_error", e.getMessage());
//			log.error("error exec query : ", e);
			return result;
		}
		return result;
	}
	
	private Map<String, Object> execute(MvcRequest req) {
		Map<String, Object> result = new HashMap<>();
		try {			
			var query = req.execute();
			var sqlQuery = req.getComposer().compose(getInstance().getStore(DemoStore.class)).buildQuery(false).sql();
			result.put("query", sqlQuery);
			result.put("result", query);
		} catch (Exception e) {
			result.put("test_error", e.getMessage());
			log.error("error exec query : ", e);
			return result;
		}
		return result;
	}
}
