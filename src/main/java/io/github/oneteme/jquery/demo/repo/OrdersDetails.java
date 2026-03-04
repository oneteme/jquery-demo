package io.github.oneteme.jquery.demo.repo;

import org.usf.jquery.core.ViewColumn;
import org.usf.jquery.web.proxy.Bind;
import org.usf.jquery.web.proxy.DatasetResource;

public interface OrdersDetails extends DatasetResource {

	@Bind("ORDER_DETAIL_ID")
	ViewColumn id();
	
	@Bind("ORDER_ID")
	ViewColumn order_id();
	
	@Bind("PRODUCT_ID")
	ViewColumn product_id();

	@Bind("QUANTITY")
	ViewColumn quantity();
}
