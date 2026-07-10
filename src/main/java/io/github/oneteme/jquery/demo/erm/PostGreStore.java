package io.github.oneteme.jquery.demo.erm;

import static org.usf.jquery.core.JDBCType.BIGINT;
import static org.usf.jquery.core.JDBCType.DOUBLE;
import static org.usf.jquery.core.Operators.function;
import static org.usf.jquery.core.Parameter.required;

import org.usf.jquery.core.OperatorDefinition;
import org.usf.jquery.mvc.Expose;

public interface PostGreStore extends DemoStore {

	@Expose(identity = "factorial", description = "Raises a numeric value to a specified power")
	default OperatorDefinition factorial() {
		return function(DOUBLE, "factorial", required(BIGINT));
	}
}
