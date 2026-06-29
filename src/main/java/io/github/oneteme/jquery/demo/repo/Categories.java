package io.github.oneteme.jquery.demo.repo;

import org.usf.jquery.core.ViewColumn;
import org.usf.jquery.mvc.Bind;
import org.usf.jquery.mvc.DatasetResource;

public interface Categories extends DatasetResource {
	
	@Bind("CATEGORY_ID")
	ViewColumn id();
	
	@Bind("CATEGORY_NAME")
	ViewColumn name();
	
	@Bind("DESCRIPTION")
	ViewColumn description();

}
