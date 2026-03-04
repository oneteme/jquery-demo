package io.github.oneteme.jquery.demo;

import static org.usf.jquery.core.ViewJoin.innerJoin;
import static org.usf.jquery.core.ViewJoin.leftJoin;
import static org.usf.jquery.core.ViewJoin.rightJoin;

import java.util.function.Function;

import org.usf.jquery.core.Criteria;
import org.usf.jquery.core.ViewJoin;
import org.usf.jquery.web.Builder;
import org.usf.jquery.web.ColumnDecorator;
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
	public Criteria criteria(String name, String... args) {
		return ViewDecorator.super.criteria(name, args);
	}
	@Override
	public String columnName(ColumnDecorator cd) {
		return colMap.apply((JQDemoColumn) cd);
	}

	@Override
	public Builder<ViewDecorator, ViewJoin[]> joinBuilder(String name) {
		if (ORDER == this && "innercustomer".equals(name)) {
			return (vd, env) -> new ViewJoin[] { innerJoin(CUSTOMER.view(), ORDER
					.column(JQDemoColumn.CUSTOMER_ID).eq(CUSTOMER.column(JQDemoColumn.ID))) };
		}
		if (ORDER == this && "leftcustomer".equals(name)) {
			return (vd, env) -> new ViewJoin[] { leftJoin(CUSTOMER.view(), ORDER
					.column(JQDemoColumn.CUSTOMER_ID).eq(CUSTOMER.column(JQDemoColumn.ID))) };
		}
		if (ORDER == this && "rightcustomer".equals(name)) {
			return (vd, env) -> new ViewJoin[] { rightJoin(CUSTOMER.view(), ORDER
					.column(JQDemoColumn.CUSTOMER_ID).eq(CUSTOMER.column(JQDemoColumn.ID))) };
		}
		return (vd, env) ->ViewDecorator.super.join(name);
//		return joins == null ? ViewDecorator.super.join(name) : joins.apply(name);
	}

}
