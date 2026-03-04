package io.github.oneteme.jquery.demo.repo;

import org.usf.jquery.core.ViewColumn;
import org.usf.jquery.web.proxy.Bind;
import org.usf.jquery.web.proxy.DatasetResource;

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
