package io.github.oneteme.jquery.demo;

import static org.usf.jquery.core.ViewJoin.innerJoin;

import java.util.function.Function;

import org.usf.jquery.core.DBFilter;
import org.usf.jquery.core.ViewJoin;
import org.usf.jquery.web.ColumnDecorator;
import org.usf.jquery.web.CriteriaBuilder;
import org.usf.jquery.web.JoinBuilder;
import org.usf.jquery.web.ViewDecorator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum JQDemoTable implements ViewDecorator {
	CUSTOMER(DataConstants::customerColumns),
	SHIPPER(DataConstants::shippersColumns),
	CATEGORY(DataConstants::categoriesColumns),
	SUPPLIER(DataConstants::suppliersColumns),
	ORDER(DataConstants::ordersColumns),
	PRODUCT(DataConstants::productsColumns),
	ORDER_DETAIL(DataConstants::ordersDetailsColumns),
	EMPLOYEE(DataConstants::employeesColumns),;

	private final Function<JQDemoColumn, String> colMap;
//	private final Function<String, JoinBuilder> joins;

//	private JQDemoTable(Function<JQDemoColumn, String> colMap) {
//		this.colMap = colMap;
////		this.joins = null;
//	}

	@Override
	public String identity() {
		return name().toLowerCase();
	}

	@Override
	public CriteriaBuilder<DBFilter> criteria(String name) { // TODO split
		return ViewDecorator.super.criteria(name);
	}

	@Override
	public String columnName(ColumnDecorator cd) {
		return colMap.apply((JQDemoColumn) cd);
	}

	@Override
	public JoinBuilder join(String name) {
		if (ORDER == this && "innercustomer".equals(name)) {
			return () -> new ViewJoin[] { ViewJoin.innerJoin(JQDemoTable.CUSTOMER.view(), JQDemoTable.ORDER
					.column(JQDemoColumn.CUSTOMER_ID).eq(JQDemoTable.CUSTOMER.column(JQDemoColumn.ID))) };
		}
		if (ORDER == this && "leftcustomer".equals(name)) {
			return () -> new ViewJoin[] { ViewJoin.leftJoin(JQDemoTable.CUSTOMER.view(), JQDemoTable.ORDER
					.column(JQDemoColumn.CUSTOMER_ID).eq(JQDemoTable.CUSTOMER.column(JQDemoColumn.ID))) };
		}
		if (ORDER == this && "rightcustomer".equals(name)) {
			return () -> new ViewJoin[] { ViewJoin.rightJoin(JQDemoTable.CUSTOMER.view(), JQDemoTable.ORDER
					.column(JQDemoColumn.CUSTOMER_ID).eq(JQDemoTable.CUSTOMER.column(JQDemoColumn.ID))) };
		}
		if (CUSTOMER == this && "rightorder".equals(name)) {
			return () -> new ViewJoin[] { ViewJoin.rightJoin(JQDemoTable.ORDER.view(), JQDemoTable.ORDER
					.column(JQDemoColumn.CUSTOMER_ID).eq(JQDemoTable.CUSTOMER.column(JQDemoColumn.ID))) };
		}
		return ViewDecorator.super.join(name);
//		return joins == null ? ViewDecorator.super.join(name) : joins.apply(name);
	}

}
