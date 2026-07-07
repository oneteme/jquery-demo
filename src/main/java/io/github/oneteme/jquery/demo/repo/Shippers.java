package io.github.oneteme.jquery.demo.repo;

import org.usf.jquery.core.ViewColumn;
import org.usf.jquery.mvc.Bind;
import org.usf.jquery.mvc.DatasetCatalog;

public interface Shippers extends DatasetCatalog {
	
	@Bind("SHIPPER_ID")
	ViewColumn id();
	
	@Bind("SHIPPER_NAME")
	ViewColumn name();
	
	@Bind("PHONE")
	ViewColumn phone();
}
