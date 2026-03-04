package io.github.oneteme.jquery.demo.repo;

import org.usf.jquery.core.ViewColumn;
import org.usf.jquery.web.proxy.Bind;
import org.usf.jquery.web.proxy.DatasetResource;

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
	
	@Bind("POSTAL_CODE")
	ViewColumn postal_code();
	
	@Bind("COUNTRY")
	ViewColumn country();
	
	//create partition, join
	
	
	
}
