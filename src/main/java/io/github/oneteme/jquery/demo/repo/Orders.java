package io.github.oneteme.jquery.demo.repo;

import static org.usf.jquery.core.Join.innerJoin;
import static org.usf.jquery.core.Join.rightJoin;
import static org.usf.jquery.core.JoinGroup.joins;
import static org.usf.jquery.web.proxy.StoreManager.getInstance;

import org.usf.jquery.core.JoinGroup;
import org.usf.jquery.core.ViewColumn;
import org.usf.jquery.web.proxy.Bind;
import org.usf.jquery.web.proxy.DatasetResource;
import org.usf.jquery.web.proxy.Expose;

public interface Orders extends DatasetResource {
	
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
		var employees = getInstance().getStore(DemoStore.class).employees();
		return joins(rightJoin(employees.getView(), employeeId().eq(employees.id())));
	}
	
	default JoinGroup innerCustomer() {
		var cust = getInstance().getStore(DemoStore.class).customers();
		return joins(innerJoin(cust.getView(), customerId().eq(cust.id())));
	}
	
	default JoinGroup innerShipper() {
		var ship = getInstance().getStore(DemoStore.class).shippers();
		return joins(innerJoin(ship.getView(), shipperId().eq(ship.id())));
	}
	
	default JoinGroup innerShippCust() {
		var ship = getInstance().getStore(DemoStore.class).shippers();
		var cust = getInstance().getStore(DemoStore.class).customers();
		return joins(
				innerJoin(cust.getView(), customerId().eq(cust.id())),
				innerJoin(ship.getView(), shipperId().eq(ship.id())));
	}
}
