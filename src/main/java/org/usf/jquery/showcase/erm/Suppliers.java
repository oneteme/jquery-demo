package org.usf.jquery.showcase.erm;

import org.usf.jquery.core.ViewColumn;
import org.usf.jquery.mvc.Bind;
import org.usf.jquery.mvc.DatasetCatalog;
import org.usf.jquery.mvc.Expose;

public interface Suppliers extends DatasetCatalog {
	
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
	
	@Expose(identity="postal_code")
	@Bind("POSTAL_CODE")
	ViewColumn postalCode();
	
	@Bind("COUNTRY")
	ViewColumn country();
	
	@Bind("PHONE")
	ViewColumn phone();
}
