package io.github.oneteme.jquery.demo;

import static org.usf.jquery.core.ViewJoin.innerJoin;

import org.usf.jquery.core.ViewJoin;
import org.usf.jquery.web.JoinBuilder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataConstants {

	static String customerColumns(JQDemoColumn column) {
		return switch (column) {
		case ID -> "CUSTOMER_ID";
		case CUSTOMER -> "CUSTOMER_NAME";
		case CONTACT -> "CONTACT_NAME";
		case ADDRESS -> "ADDRESS";
		case CITY -> "CITY";
		case POSTAL_CODE -> "POSTAL_CODE";
		case COUNTRY -> "COUNTRY";
		default -> null;
		};
	}

	static String employeesColumns(JQDemoColumn column) {
		return switch (column) {
		case ID -> "EMPLOYEE_ID";
		case LAST_NAME -> "LAST_NAME";
		case NAME -> "FIRST_NAME";
		case START -> "BIRTH_DATE";
		case PHOTO -> "PHOTO";
		case NOTES -> "NOTES";
		default -> null;
		};
	}

	static String shippersColumns(JQDemoColumn column) {
		return switch (column) {
		case ID -> "SHIPPER_ID";
		case NAME -> "SHIPPER_NAME";
		case PHONE -> "PHONE";
		default -> null;
		};
	}

	static String categoriesColumns(JQDemoColumn column) {
		return switch (column) {
		case ID -> "CATEGORY_ID";
		case NAME -> "CATEGORY_NAME";
		case DESCRIPTION -> "DESCRIPTION";
		default -> null;
		};
	}

	static String suppliersColumns(JQDemoColumn column) {
		return switch (column) {
		case ID -> "SUPPLIER_ID";
		case NAME -> "SUPPLIER_NAME";
		case CONTACT -> "CONTACT_NAME";
		case ADDRESS -> "ADDRESS";
		case CITY -> "CITY";
		case POSTAL_CODE -> "POSTAL_CODE";
		case COUNTRY -> "COUNTRY";
		case PHONE -> "PHONE";
		default -> null;
		};
	}

	static String ordersColumns(JQDemoColumn column) {
		return switch (column) {
		case ID -> "ORDER_ID";
		case START -> "ORDER_DATE";
		case CUSTOMER_ID -> "CUSTOMER_ID";
		case EMPLOYEE_ID -> "EMPLOYEE_ID";
		case SHIPPER_ID -> "SHIPPER_ID";
		default -> null;
		};
	}

	static String productsColumns(JQDemoColumn column) {
		return switch (column) {
		case ID -> "PRODUCT_ID";
		case NAME -> "PRODUCT_NAME";
		case SUPPLIER_ID -> "SUPPLIER_ID";
		case CATEGORY_ID -> "CATEGORY_ID";
		case PRICE -> "PRICE";
		case UNIT -> "UNIT";
		default -> null;
		};
	}

	static String ordersDetailsColumns(JQDemoColumn column) {
		return switch (column) {
		case ID -> "ORDER_DETAIL_ID";
		case ORDER_ID -> "ORDER_ID";
		case PRODUCT_ID -> "PRODUCT_ID";
		case QUANTITY -> "QUANTITY";
		default -> null;
		};
	}

	public static JoinBuilder orderJoin(String name) {
		return switch (name) {
//		case "complete" -> () -> new ViewJoin[] { joinDepRegion(), joinConvDep() };
//		case "depregion" -> () -> new ViewJoin[] { joinDepRegion() };
//		case "region" -> () -> joinConvReg();
//		case "ratt" -> () -> joinConvRatt(RATT);
//		case "resp" -> () -> joinConvResp();
//		case "type" -> () -> joinConvType(AutoConsTable.TYPE);
		case "innercustomer" -> () -> new ViewJoin[] { joinOrderCustomer(JQDemoTable.CUSTOMER) };
		default -> null;
		};
	}

	public static ViewJoin joinOrderCustomer(JQDemoTable view) {
		return innerJoin(view.view(),JQDemoTable.ORDER.column(JQDemoColumn.CUSTOMER_ID).eq(JQDemoTable.CUSTOMER.column(JQDemoColumn.ID)));
	}

}
