package io.github.oneteme.jquery.demo.repo;

import org.usf.jquery.core.ViewColumn;
import org.usf.jquery.mvc.Bind;
import org.usf.jquery.mvc.DatasetCatalogue;

public interface Shippers extends DatasetCatalogue {
	
	@Bind("SHIPPER_ID")
	ViewColumn id();
	
	@Bind("SHIPPER_NAME")
	ViewColumn name();
	
	@Bind("PHONE")
	ViewColumn phone();
}
