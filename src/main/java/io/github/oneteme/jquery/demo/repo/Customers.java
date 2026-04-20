package io.github.oneteme.jquery.demo.repo;

import static org.usf.jquery.core.JoinsClause.joins;
import static org.usf.jquery.core.ViewJoin.leftJoin;
import static org.usf.jquery.web.proxy.StoreManager.getInstance;

import org.usf.jquery.core.JoinsClause;
import org.usf.jquery.core.ViewColumn;
import org.usf.jquery.web.proxy.Bind;
import org.usf.jquery.web.proxy.DatasetResource;
import org.usf.jquery.web.proxy.Expose;

public interface Customers extends DatasetResource {

	@Bind("CUSTOMER_ID")
	ViewColumn id();
	
	@Bind("CUSTOMER_NAME")
	ViewColumn name();
	
	@Bind("CONTACT_NAME")
	ViewColumn contact();
	
	@Bind("ADDRESS")
	ViewColumn address();
	
	@Bind("CITY")
	ViewColumn city();
	
	@Expose(identity="postal_code")
	@Bind("POSTAL_CODE")
	ViewColumn postalCode();
	
	@Bind("COUNTRY")
	ViewColumn country();
	
	//create partition, join
	
	default JoinsClause leftOrder() {
		var orders = getInstance().getStore(DemoStore.class).orders();
		return joins(leftJoin(orders.getView(), id().eq(orders.customerId())));
	}
	
}
