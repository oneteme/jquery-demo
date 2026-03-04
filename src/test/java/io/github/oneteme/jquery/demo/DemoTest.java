package io.github.oneteme.jquery.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.usf.jquery.web.proxy.EntryEvaluators.evaluateColumn;
import static org.usf.jquery.web.proxy.EntryEvaluators.evaluateFilter;
import static org.usf.jquery.web.proxy.EntryEvaluators.evaluateJoin;
import static org.usf.jquery.web.proxy.EntryEvaluators.evaluateView;
import static org.usf.jquery.web.proxy.EntryParser.parseEntries;
import static org.usf.jquery.web.proxy.EntryParser.parseEntry;
import static org.usf.jquery.web.proxy.StoreManager.getInstance;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import io.github.oneteme.jquery.demo.repo.DemoStore;

class DemoTest {

	@BeforeEach
	void init() {
		getInstance().register(DemoStore.class, null);
	}

	@ParameterizedTest
	@MethodSource("columnTestCases")
	void testEvaluateColumn(String store, String entry, String expected) {
		var ctx = getInstance().getDefaultStore().createContext(store);
		assertEquals(expected, evaluateColumn(parseEntry(entry), ctx).toString());
	}

	static Stream<Arguments> columnTestCases() {
		return Stream.of(
				Arguments.of("products", "price", "PRICE"),
				Arguments.of("products", "price.sum", "SUM(PRICE)"),
				Arguments.of("products", "price.avg", "AVG(PRICE)"),
				Arguments.of("products", "price.count", "COUNT(PRICE)"),
				Arguments.of("products", "price.min", "MIN(PRICE)"),
				Arguments.of("products", "price.max", "MAX(PRICE)"),
				
				Arguments.of("products", "price.trunc", "TRUNC(PRICE)"),
				Arguments.of("products", "price.abs", "ABS(PRICE)"),
				Arguments.of("products", "price.ceil", "CEIL(PRICE)"),
				Arguments.of("products", "price.floor", "FLOOR(PRICE)"),
				Arguments.of("products", "price.round", "ROUND(PRICE)"),
				Arguments.of("products", "price.sqrt", "SQRT(PRICE)"),
				Arguments.of("products", "price.mod(2)", "MOD(PRICE, 2.0)"),

				Arguments.of("products", "price.plus(2)", "(PRICE+2)")
				);
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
