package io.github.oneteme.jquery.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.usf.jquery.web.proxy.EntryEvaluators.*;
import static org.usf.jquery.web.proxy.EntryParser.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.usf.jquery.web.proxy.EntryEvaluators;
import org.usf.jquery.web.proxy.EntryParser;
import org.usf.jquery.web.proxy.Stores;

import io.github.oneteme.jquery.demo.repo.DemoStore;

class DemoTest {
	@BeforeEach
	void init() {
		Stores.register(DemoStore.class, null);
	}
	

	@Test
	void testEval() {
		var ctx = Stores.getDefaultSchema().createContext("orders");
		
		//assertEquals("CUSTOMER_ID", evaluateColumn(parseEntry("id"), ctx).toString());
//		assertEquals("SUM(CUSTOMER_ID)", evaluateColumn(parseEntry("id.sum"), ctx).toString());
		assertEquals("LEFT JOIN demo.CUSTOMERS_TABLE  ON CUSTOMER_ID=CUSTOMER_ID ", evaluateJoin(parseEntry("leftcustomer"), ctx).toString());
	}

	//@Test
	void testEvaluateView() {
		var ctx = Stores.getDefaultSchema().createContext("v1");
		
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
