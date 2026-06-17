package io.github.oneteme.jquery.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.of;
import static org.usf.jquery.web.proxy.EntryEvaluators.evaluateFilter;
import static org.usf.jquery.web.proxy.EntryEvaluators.evaluateJoin;
import static org.usf.jquery.web.proxy.EntryEvaluators.evaluateView;
import static org.usf.jquery.web.proxy.EntryParser.parseEntries;
import static org.usf.jquery.web.proxy.EntryParser.parseEntry;
import static org.usf.jquery.web.proxy.StoreManager.getInstance;

import java.util.function.BiFunction;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.usf.jquery.core.QueryPart;
import org.usf.jquery.web.proxy.Entry;
import org.usf.jquery.web.proxy.EntryEvaluators;
import org.usf.jquery.web.proxy.RequestContext;

import io.github.oneteme.jquery.demo.repo.DemoStore;

class DemoTest {

	@BeforeAll
	static void init() {
		getInstance().register(DemoStore.class, null);
	}

	private String evaluate(String ds, String entry, BiFunction<Entry, RequestContext, QueryPart> evaluator) {
		var ctx = getInstance().getDefaultStore().createContext(ds);
		return evaluator.apply(parseEntry(entry), ctx).toString();
	}
	
	@ParameterizedTest
	@MethodSource("viewTestCases")
	void testEvaluateView(String ds, String expected) {
		assertEquals(expected, evaluate(ds, ds, EntryEvaluators::evaluateView));
	}

	@ParameterizedTest
	@CsvSource(delimiter = ';', value = {
			
		
		"price; PRICE",
		
		//aggregate functions
		"price.sum; SUM(PRICE)",
		"price.avg; AVG(PRICE)",
		"price.count; COUNT(PRICE)",
		"price.min; MIN(PRICE)",
		"price.max; MAX(PRICE)",
		
		// Arithmetic Operators
		"price.plus(2); (PRICE+2)",
		"price.minus(2); (PRICE-2)",
		"price.multiply(2); (PRICE*2)",
		"price.divide(2); (PRICE/2)",
		
		// numeric functions
		"price.sqrt; SQRT(PRICE)",
		"price.exp; EXP(PRICE)",
		"price.log; LOG(PRICE)",
		"price.log(2); LOG(PRICE, 2)",
		"price.abs; ABS(PRICE)",
		"price.ceil; CEIL(PRICE)",
		"price.floor; FLOOR(PRICE)",
		"price.trunc; TRUNC(PRICE)",
		"price.trunc(2); TRUNC(PRICE, 2)",
		"price.round; ROUND(PRICE)",
		"price.round(2); ROUND(PRICE, 2)",
		"price.mod(2); MOD(PRICE, 2.0)",
		"price.pow(2); POWER(PRICE, 2.0)", // Has been overridden for H2 POW -> POWER
		
		// bit functions
		"price.bitAnd(2); (PRICE&2)",
		"price.bitOr(2); (PRICE|2)",
		"price.bitXor(2); (PRICE#2)",
		//"price.bitNot; (PRICE~)", TODO : wrong Exception -> Arithmetic exception takes 2 arguments
		"price.bitShiftLeft(2); (PRICE<<2)",
		"price.bitShiftRight(2); (PRICE>>2)",
		
		// string functions
		"name.length;LENGTH(PRODUCT_NAME)",
		"name.trim;TRIM(PRODUCT_NAME)",
		"name.ltrim;LTRIM(PRODUCT_NAME)",
		"name.rtrim;RTRIM(PRODUCT_NAME)",
		"name.upper;UPPER(PRODUCT_NAME)",
		"name.lower;LOWER(PRODUCT_NAME)",
		"name.initcap;INITCAP(PRODUCT_NAME)",
		"name.reverse;REVERSE(PRODUCT_NAME)",
		"name.left(2);LEFT(PRODUCT_NAME, 2)",
		"name.right(2);RIGHT(PRODUCT_NAME, 2)",
		"name.replace(toto,titi);REPLACE(PRODUCT_NAME, 'toto', 'titi')",
		"name.substring(1,2);SUBSTRING(PRODUCT_NAME, 1, 2)",
		"name.concat(toto);CONCAT(PRODUCT_NAME, 'toto')",
		"name.concat(toto,titi);CONCAT(PRODUCT_NAME, 'toto', 'titi')",
		"name.concat(toto,titi,tata);CONCAT(PRODUCT_NAME, 'toto', 'titi', 'tata')",
		"name.lpad(1,toto); LPAD(PRODUCT_NAME, 1, 'toto')",
		"lpad(20,1,toto); LPAD(20, 1, 'toto')",
		"name.rpad(1,toto); RPAD(PRODUCT_NAME, 1, 'toto')",
		"rpad(20,1,toto); RPAD(20, 1, 'toto')",
		"orders.start.age;AGE(ORDER_DATE)",
		"orders.start.age(cdate);AGE(ORDER_DATE, CURRENT_DATE)",
		"orders.start.age(ctimestamp);AGE(ORDER_DATE, CURRENT_TIMESTAMP)",
		
		// temporal functions
		"orders.start.year; EXTRACT(YEAR FROM ORDER_DATE)",
		"orders.start.month; EXTRACT(MONTH FROM ORDER_DATE)",
		"orders.start.week; EXTRACT(WEEK FROM ORDER_DATE)",
		"orders.start.day; EXTRACT(DAY FROM ORDER_DATE)",
		"orders.start.dow; EXTRACT(DOW FROM ORDER_DATE)",
		"orders.start.doy; EXTRACT(DOY FROM ORDER_DATE)",
		"orders.start.hour; EXTRACT(HOUR FROM ORDER_DATE)",
		"orders.start.minute; EXTRACT(MINUTE FROM ORDER_DATE)",
		"orders.start.second; EXTRACT(SECOND FROM ORDER_DATE)",
		"orders.start.epoch; EXTRACT(EPOCH FROM ORDER_DATE)",
		
		//combined functions
		"orders.start.semester; CASE WHEN EXTRACT(MONTH FROM ORDER_DATE)<7 THEN 1 ELSE 2 END",
		"orders.start.quarter; CASE WHEN EXTRACT(MONTH FROM ORDER_DATE)<4 THEN 1 WHEN EXTRACT(MONTH FROM ORDER_DATE)<7 THEN 2 WHEN EXTRACT(MONTH FROM ORDER_DATE)<10 THEN 3 ELSE 4 END",
		"orders.start.yearSemester; CONCAT(CAST(EXTRACT(YEAR FROM ORDER_DATE) AS VARCHAR), '-S', CAST(CASE WHEN EXTRACT(MONTH FROM ORDER_DATE)<7 THEN 1 ELSE 2 END AS VARCHAR))",
		"orders.start.yearQuarter; CONCAT(CAST(EXTRACT(YEAR FROM ORDER_DATE) AS VARCHAR), '-Q', CAST(CASE WHEN EXTRACT(MONTH FROM ORDER_DATE)<4 THEN 1 WHEN EXTRACT(MONTH FROM ORDER_DATE)<7 THEN 2 WHEN EXTRACT(MONTH FROM ORDER_DATE)<10 THEN 3 ELSE 4 END AS VARCHAR))",
		"orders.start.yearWeek; CONCAT(CAST(EXTRACT(YEAR FROM ORDER_DATE) AS VARCHAR), '-W', LPAD(CAST(EXTRACT(DOY FROM ORDER_DATE) AS VARCHAR), 2, '0'))",
		"orders.start.yearMonth; LEFT(CAST(ORDER_DATE AS VARCHAR), 7)",
		"orders.start.monthDay; SUBSTRING(CAST(ORDER_DATE AS VARCHAR), 6, 5)",
		"orders.start.hourMinute; LEFT(CAST(CAST(ORDER_DATE AS TIME) AS VARCHAR), 5)",
		
		//Cast functions
		"price.varchar;CAST(PRICE AS VARCHAR)",
		"price.integer;CAST(PRICE AS INTEGER)",
		"price.bigint;CAST(PRICE AS BIGINT)",
		"price.decimal;CAST(PRICE AS DECIMAL)",
		"price.bool;CAST(PRICE AS BOOLEAN)",
		"orders.start.timestamp;CAST(ORDER_DATE AS TIMESTAMP)",
		"orders.start.date;CAST(ORDER_DATE AS DATE)",
		"orders.start.time;CAST(ORDER_DATE AS TIME)",
		
		//window functions
		"rank;RANK()",
		"rowNumber;ROW_NUMBER()",
		"denseRank;DENSE_RANK()",
		"percentRank;PERCENT_RANK()",
		"rank.over;RANK() OVER()",
		"rowNumber.over;ROW_NUMBER() OVER()",
		"denseRank.over;DENSE_RANK() OVER()",
		"percentRank.over;PERCENT_RANK() OVER()",
		"price.over;PRICE OVER()",
//		"rank.over(partition(id).order(price.desc));RANK OVER(PARTITION)", TODO : test over with partition
		
		// Other functions
		"orders.start.coalesce(cdate);COALESCE(ORDER_DATE, CURRENT_DATE)",
		"price.distinct;DISTINCT(PRICE)",
		
		// Constants
		"cdate; CURRENT_DATE",
		"ctimestamp; CURRENT_TIMESTAMP",
		"ctime; CURRENT_TIME"
	})
	void testEvaluateColumn(String entry, String expected) {
		assertEquals(expected, evaluate("products", entry, EntryEvaluators::evaluateColumn));
	}

	@ParameterizedTest
	@CsvSource(delimiter = ';', ignoreLeadingAndTrailingWhitespace = false, value = {
	"leftCustomer;LEFT JOIN CUSTOMERS_TABLE  ON CUSTOMER_ID=CUSTOMER_ID",/*TODO : an additional space after CUSTOMERS_TABLE and at the end of the query*/
	"rightCustomer;RIGHT JOIN CUSTOMERS_TABLE  ON CUSTOMER_ID=CUSTOMER_ID",
	"innerCustomer;INNER JOIN CUSTOMERS_TABLE  ON CUSTOMER_ID=CUSTOMER_ID"
	})
	void testEvaluateJoin( String entry, String expected) {
		assertEquals(expected, evaluate("orders", entry, EntryEvaluators::evaluateJoin));
	}

	@ParameterizedTest
	@CsvSource(delimiter = ';', value = { "products;price;PRICE" })
	void testEvaluateOrder(String store, String entry, String expected) {
		assertEquals(expected, evaluate(store, entry, EntryEvaluators::evaluateOrder));
	}

	static Stream<Arguments> viewTestCases() {
		return Stream.of(
				of("customers","CUSTOMERS_TABLE"),
				of("categories","CATEGORIES_TABLE"),
				of("employees","EMPLOYEES_TABLE"),
				of("products","PRODUCTS_TABLE"),
				of("orders_details","ORDERS_DETAILS_TABLE"),
				of("suppliers","SUPPLIERS_TABLE")
				
				);
		}



	private void assertThrowsMessage(Executable code) {
		var ex = assertThrows(IllegalArgumentException.class, code);
//	    assertEquals(msg, ex.getMessage());
	}
//	@ParameterizedTest
//	@MethodSource("failTestCases")
//	void testEvaluateFails(String store, String entry, String expected) {
//		var ctx = getInstance().getDefaultStore().createContext(store);
//		assertThrowsMessage(()->evaluate(store, entry, expected, EntryEvaluators::evaluateOrder));
//	}
	
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
