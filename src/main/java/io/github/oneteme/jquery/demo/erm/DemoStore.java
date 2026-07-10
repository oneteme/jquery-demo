package io.github.oneteme.jquery.demo.erm;

import static org.usf.jquery.core.JDBCType.DOUBLE;
import static org.usf.jquery.core.Mappers.keyValueMapper;
import static org.usf.jquery.core.Operators.aggregate;
import static org.usf.jquery.core.Operators.constant;
import static org.usf.jquery.core.Predicate.ge;
import static org.usf.jquery.core.Predicate.lt;

import java.util.HashMap;

import org.usf.jquery.core.Chainable;
import org.usf.jquery.core.Column;
import org.usf.jquery.core.OperatorDefinition;
import org.usf.jquery.core.Predicate;
import org.usf.jquery.core.QueryComposer;
import org.usf.jquery.mvc.Bind;
import org.usf.jquery.mvc.Expose;
import org.usf.jquery.mvc.QueryCatalog;
import org.usf.jquery.mvc.StoreCatalog;
import org.usf.jquery.mvc.ViewRegistry;

public interface DemoStore extends StoreCatalog {

	static ViewRegistry registry = new ViewRegistry().register("debug", rsp -> (qc, str) -> {
		var res = new HashMap<String, Object>();
		var query = qc.compose(str);
		res.put("query", query.buildQuery(false).sql()); // rename to sql
		try {
			res.put("result", str.execute(query, keyValueMapper())); // rename to "data"
		} catch (Exception e) {
			res.put("test_error", e.getMessage()); // TODO rename to "error"
			log.error("error exec query : ", e);
		}
		return res;
	});

	@Bind("CUSTOMERS_TABLE")
	Customers customers();

	@Bind("SHIPPERS_TABLE")
	Shippers shippers();

	@Bind("CATEGORIES_TABLE")
	Categories categories();

	@Bind("SUPPLIERS_TABLE")
	Suppliers suppliers();

	@Bind("ORDERS_TABLE")
	Orders orders();

	@Bind("PRODUCTS_TABLE")
	Products products();

	@Expose(identity = "orders_details", description = "")
	@Bind("ORDERS_DETAILS_TABLE")
	OrdersDetails ordersDetails();

	@Bind("EMPLOYEES_TABLE")
	Employees employees();

//	default QueryView testView() {
//		var v = categories().getView();
//		return new QueryComposer()
//				.columns(Column.allColumns(v))
//				.filters()
//				.compose();
//	}

	default QueryCatalog subCategories() {
		return new QueryCatalog(new QueryComposer().columns(categories().id())
				.criteria(categories().name().startsLike("Con")).compose(this));
	}

	@Expose(identity = "test_mode", description = "")
	default OperatorDefinition mode() {
		return aggregate(DOUBLE, "MODE");
	}

	@Expose(identity = "pi", description = "")
	default OperatorDefinition pi() {
		return constant(DOUBLE, "PI()");
	}

	@Expose(identity = "random", description = "")
	default OperatorDefinition random() {
		return constant(DOUBLE, "RANDOM()");
	}

	default Column getRandom() {
		return dialect().ctimestamp().invoke();
	}

	@Expose(identity = "category")
	default Predicate priceCategory(String... values) {
		return Chainable.or(values, v -> switch (v) {
		case "cheap" -> lt(10);
		case "medium" -> ge(10).and(lt(20));
		case "expensive" -> ge(20);
		default -> null;
		});
	}

	@Override
	default ViewRegistry viewRegistry() {
		return registry;
	}
}
