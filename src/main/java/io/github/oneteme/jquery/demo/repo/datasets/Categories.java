package io.github.oneteme.jquery.demo.repo.datasets;

import org.usf.jquery.core.ViewColumn;
import org.usf.jquery.mvc.Bind;
import org.usf.jquery.mvc.DatasetCatalog;

public interface Categories extends DatasetCatalog {
	
	@Bind("CATEGORY_ID")
	ViewColumn id();
	
	@Bind("CATEGORY_NAME")
	ViewColumn name();
	
	@Bind("DESCRIPTION")
	ViewColumn description();

}
