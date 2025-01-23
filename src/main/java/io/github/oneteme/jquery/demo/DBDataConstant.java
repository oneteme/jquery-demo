package io.github.oneteme.jquery.demo;

import org.usf.jquery.web.ViewDecorator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class DBDataConstant {
	
	static String demoViews(ViewDecorator vd) {
		return switch ((JQDemoTable) vd) {
		case CUSTOMER: yield "CUSTOMERS_TABLE";
		case SHIPPER: yield "SHIPPERS_TABLE";
		case CATEGORY: yield "CATEGORIES_TABLE";
		case SUPPLIER: yield "SUPPLIERS_TABLE";
		case ORDER: yield "ORDERS_TABLE";
		case PRODUCT: yield "PRODUCTS_TABLE";
		case ORDER_DETAIL: yield "ORDERS_DETAILS_TABLE";
		case EMPLOYEE: yield "EMPLOYEES_TABLE";
		default: yield null;
		};
	}
}
