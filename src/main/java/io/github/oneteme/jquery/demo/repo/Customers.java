package io.github.oneteme.jquery.demo.repo;

import static org.usf.jquery.core.Join.leftJoin;
import static org.usf.jquery.core.JoinGroup.joins;
import static org.usf.jquery.mvc.StoreManager.getInstance;

import org.usf.jquery.core.Column;
import org.usf.jquery.core.JoinGroup;
import org.usf.jquery.core.Order;
import org.usf.jquery.core.Partition;
import org.usf.jquery.core.PartitionComposer;
import org.usf.jquery.core.ViewColumn;
import org.usf.jquery.mvc.Bind;
import org.usf.jquery.mvc.DatasetCatalog;
import org.usf.jquery.mvc.Expose;

public interface Customers extends DatasetCatalog {

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
	
	
	default JoinGroup leftOrder() {
		var orders = getInstance().getStore(DemoStore.class).orders();
		return joins(leftJoin(orders.getView(), id().eq(orders.customerId())));
	}
	
	default Column rankLocation() {
		return Column.rank().over(new PartitionComposer().columns(address()).orders(city().asc()).compose(null));
	}
	
	default Column rowLocation() {
		return Column.rowNumber().over(new PartitionComposer().columns(address()).orders(city().asc()).compose(null));
	}
	
	default Column denseLocation() {
		return Column.denseRank().over(new PartitionComposer().columns(address()).orders(city().asc()).compose(null));
	}
	
	default Column percentLocation() {
		return Column.rowNumber().over(new PartitionComposer().columns(address()).orders(city().asc()).compose(null));
	}
	
	default Order orderCol() {
		return city().asc();
	}
	
}
