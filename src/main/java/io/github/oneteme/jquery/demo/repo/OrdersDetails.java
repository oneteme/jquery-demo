package io.github.oneteme.jquery.demo.repo;

import org.usf.jquery.core.ViewColumn;
import org.usf.jquery.mvc.Bind;
import org.usf.jquery.mvc.DatasetResource;
import org.usf.jquery.mvc.Expose;

public interface OrdersDetails extends DatasetResource {

	@Bind("ORDER_DETAIL_ID")
	ViewColumn id();
	
	@Expose(identity="order_id")
	@Bind("ORDER_ID")
	ViewColumn orderId();
	
	@Expose(identity="product_id")
	@Bind("PRODUCT_ID")
	ViewColumn productId();

	@Bind("QUANTITY")
	ViewColumn quantity();
}
