package io.github.oneteme.jquery.demo.repo;

import org.usf.jquery.core.ViewColumn;
import org.usf.jquery.mvc.Bind;
import org.usf.jquery.mvc.DatasetResource;

public interface Employees extends DatasetResource {

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
