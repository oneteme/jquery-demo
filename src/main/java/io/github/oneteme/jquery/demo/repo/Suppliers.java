package io.github.oneteme.jquery.demo.repo;

import org.usf.jquery.core.ViewColumn;
import org.usf.jquery.web.proxy.Bind;
import org.usf.jquery.web.proxy.DatasetResource;

public interface Suppliers extends DatasetResource {
	
	@Bind("SUPPLIER_ID")
	ViewColumn id();
	
	@Bind("SUPPLIER_NAME")
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
	
	@Bind("PHONE")
	ViewColumn phone();
}
