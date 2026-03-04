package io.github.oneteme.jquery.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.of;
import static org.usf.jquery.web.proxy.EntryEvaluators.evaluateFilter;
import static org.usf.jquery.web.proxy.EntryEvaluators.evaluateJoin;
import static org.usf.jquery.web.proxy.EntryEvaluators.evaluateView;
import static org.usf.jquery.web.proxy.EntryParser.parseEntries;
import static org.usf.jquery.web.proxy.EntryParser.parseEntry;
import static org.usf.jquery.web.proxy.StoreManager.getInstance;

import java.util.function.BiFunction;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.usf.jquery.core.DBObject;
import org.usf.jquery.web.proxy.Entry;
import org.usf.jquery.web.proxy.EntryEvaluators;
import org.usf.jquery.web.proxy.RequestContext;

import io.github.oneteme.jquery.demo.repo.DemoStore;

class DemoTest {

	@BeforeEach
	void init() {
		getInstance().register(DemoStore.class, null);
	}

	void evaluate(String store, String entry, String expected, BiFunction<Entry, RequestContext, DBObject> evaluator) {
		var ctx = getInstance().getDefaultStore().createContext(store);
		assertEquals(expected, evaluator.apply(parseEntry(entry), ctx).toString());
	}
	@ParameterizedTest
	@MethodSource("columnTestCases")
	void testEvaluateColumn(String store, String entry, String expected) {
		evaluate(store, entry, expected, EntryEvaluators::evaluateColumn);
	}

	@ParameterizedTest
	@MethodSource("joinTestCases")
	void testEvaluateJoin(String store, String entry, String expected) {
		evaluate(store, entry, expected, EntryEvaluators::evaluateJoin);
	}

	static Stream<Arguments> columnTestCases() {
		return Stream.of(
				of("products", "price", "PRICE"),
				of("products", "price.sum", "SUM(PRICE)"),
				of("products", "price.avg", "AVG(PRICE)"),
				of("products", "price.count", "COUNT(PRICE)"),
				of("products", "price.min", "MIN(PRICE)"),
				of("products", "price.max", "MAX(PRICE)"),

				of("products", "price.trunc", "TRUNC(PRICE)"),
				of("products", "price.abs", "ABS(PRICE)"),
				of("products", "price.ceil", "CEIL(PRICE)"),
				of("products", "price.floor", "FLOOR(PRICE)"),
				of("products", "price.round", "ROUND(PRICE)"),
				of("products", "price.sqrt", "SQRT(PRICE)"),
				of("products", "price.mod(2)", "MOD(PRICE, 2.0)"),

				of("products", "price.plus(2)", "(PRICE+2)"),
				of("products", "price.minus(2)", "(PRICE-2)"),
				of("products", "price.multiply(2)", "(PRICE*2)"),
				of("products", "price.divide(2)", "(PRICE/2)"));
	}

	static Stream<Arguments> joinTestCases() {
		return Stream.of(
				of("orders", "leftcustomer", "LEFT JOIN CUSTOMERS_TABLE  ON CUSTOMER_ID=CUSTOMER_ID ")/*TODO : an additional space after CUSTOMERS_TABLE and at the end of the query*/);
	}

	// @Test
	void testEvaluateView() {
		var ctx = getInstance().getDefaultStore().createContext("v1");

		System.out.println(evaluateView(parseEntry("v1:myView"), ctx));
		System.out.println(evaluateView(parseEntry(
				"v1.column(start,end,varchar(33).myFn:txt).filter(start.gt(ctimestamp)).order(end.desc,start):v5"),
				ctx));

		System.out.println(evaluateFilter(parseEntry("size.vitesse"), ctx, parseEntries("fast,slow,fastest")));
		System.out.println(evaluateFilter(parseEntry("bool(1).eq(1)"), ctx));
		System.out.println(evaluateFilter(parseEntry("size.vitesse(fast,slow,fastest).and(bool(1).eq(1))"), ctx));

		System.out.println(evaluateFilter(parseEntry("size"), ctx, parseEntries("3")));
		System.out.println(evaluateFilter(parseEntry("size"), ctx, parseEntries("3,2,1")));
		System.out.println(evaluateFilter(parseEntry("size.lt(3)"), ctx));
		System.out.println(evaluateFilter(parseEntry("size.lt"), ctx, parseEntries("3")));
//		System.out.println(evaluateFilter(parseEntry("size.lt(3)"), ctx, parseEntries("3")));
		System.out.println(evaluateJoin(parseEntry("leftJoin(v1).filter(v1.start.eq(ctimestamp))"), ctx));
	}

}
