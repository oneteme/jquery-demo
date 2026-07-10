package org.usf.jquery.showcase.erm;

import org.usf.jquery.core.ViewColumn;
import org.usf.jquery.mvc.Bind;
import org.usf.jquery.mvc.DatasetCatalog;

public interface Employees extends DatasetCatalog {

	@Bind("EMPLOYEE_ID")
	ViewColumn id();

	@Bind("LAST_NAME")
	ViewColumn lname();

	@Bind("FIRST_NAME")
	ViewColumn fname();

	@Bind("BIRTH_DATE")
	ViewColumn start();

	@Bind("PHOTO")
	ViewColumn photo();

	@Bind("NOTES")
	ViewColumn notes();
}
