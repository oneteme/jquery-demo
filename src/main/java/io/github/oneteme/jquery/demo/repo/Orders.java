package io.github.oneteme.jquery.demo.repo;

import static org.usf.jquery.core.ViewJoin.leftJoin;
import static org.usf.jquery.web.proxy.Stores.getSchema;

import org.usf.jquery.core.JoinsClause;
import org.usf.jquery.core.ViewColumn;
import org.usf.jquery.core.ViewJoin;
import org.usf.jquery.web.proxy.Bind;
import org.usf.jquery.web.proxy.DatasetResource;

public interface Orders extends DatasetResource {
	
	@Bind("ORDER_ID")
	ViewColumn id();
	
	@Bind("ORDER_DATE")
	ViewColumn start();
	
	@Bind("CUSTOMER_ID")
	ViewColumn customer_id();
	
	@Bind("EMPLOYEE_ID")
	ViewColumn employee_id();

	@Bind("SHIPPER_ID")
	ViewColumn shipper_id();
	
	default JoinsClause leftcustomer() {
		var cust = getSchema(DemoStore.class).customers();
		return new JoinsClause(new ViewJoin[] { leftJoin(cust.getView(), customer_id().eq(cust.id()))});
	}
}
