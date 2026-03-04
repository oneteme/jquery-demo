package io.github.oneteme.jquery.demo.repo;

import org.usf.jquery.core.ViewColumn;
import org.usf.jquery.web.proxy.Bind;
import org.usf.jquery.web.proxy.DatasetResource;

public interface Shippers extends DatasetResource {
	
	@Bind("SHIPPER_ID")
	ViewColumn id();
	
	@Bind("SHIPPER_NAME")
	ViewColumn name();
	
	@Bind("PHONE")
	ViewColumn phone();
}
