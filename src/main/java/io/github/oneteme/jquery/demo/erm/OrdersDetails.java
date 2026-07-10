package io.github.oneteme.jquery.demo.erm;

import org.usf.jquery.core.ViewColumn;
import org.usf.jquery.mvc.Bind;
import org.usf.jquery.mvc.DatasetCatalog;
import org.usf.jquery.mvc.Expose;

public interface OrdersDetails extends DatasetCatalog {

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
