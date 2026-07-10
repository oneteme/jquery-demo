package org.usf.jquery.showcase.erm;

import static org.usf.jquery.core.Join.innerJoin;
import static org.usf.jquery.core.Join.rightJoin;
import static org.usf.jquery.core.JoinGroup.joins;
import static org.usf.jquery.mvc.StoreManager.getInstance;

import org.usf.jquery.core.JoinGroup;
import org.usf.jquery.core.ViewColumn;
import org.usf.jquery.mvc.Bind;
import org.usf.jquery.mvc.DatasetCatalog;
import org.usf.jquery.mvc.Expose;

public interface Orders extends DatasetCatalog {
	
	@Bind("ORDER_ID")
	ViewColumn id();
	
	@Bind("ORDER_DATE")
	ViewColumn start();
	
	@Expose(identity="customer_id")
	@Bind("CUSTOMER_ID")
	ViewColumn customerId();
	
	@Expose(identity="employee_id")
	@Bind("EMPLOYEE_ID")
	ViewColumn employeeId();

	@Expose(identity="shipper_id")
	@Bind("SHIPPER_ID")
	ViewColumn shipperId();
	
	default JoinGroup rightEmployee() {
		var employees = currentStore().employees();
		return joins(rightJoin(employees.getView(), employeeId().eq(employees.id())));
	}
	
	default JoinGroup innerCustomer() {
		var cust = currentStore().customers();
		return joins(innerJoin(cust.getView(), customerId().eq(cust.id())));
	}
	
	default JoinGroup innerShipper() {
		var ship = currentStore().shippers();
		return joins(innerJoin(ship.getView(), shipperId().eq(ship.id())));
	}
	
	default JoinGroup innerShippCust() {
		var ship = currentStore().shippers();
		var cust = currentStore().customers();
		return joins(
				innerJoin(cust.getView(), customerId().eq(cust.id())),
				innerJoin(ship.getView(), shipperId().eq(ship.id())));
	}

	default DemoStore currentStore() {
		return getInstance().getStore(DemoStore.class);
	}
	
}
