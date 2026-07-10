package io.github.oneteme.jquery.demo.erm;

import static org.usf.jquery.core.JDBCType.DOUBLE;
import static org.usf.jquery.core.Operators.function;
import static org.usf.jquery.core.Parameter.required;

import org.usf.jquery.core.OperatorDefinition;
import org.usf.jquery.mvc.Expose;

public interface H2Store extends DemoStore {

	
	@Expose(identity = "pow", description = "Raises a numeric value to a specified power")
	default OperatorDefinition pow() {
		return function(DOUBLE, "POWER", required(DOUBLE), required(DOUBLE));
	}
}
