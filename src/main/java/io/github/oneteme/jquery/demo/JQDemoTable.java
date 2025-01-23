package io.github.oneteme.jquery.demo;

import java.util.function.Function;

import org.usf.jquery.core.DBFilter;
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
	ORDER(DataConstants::ordersColumns,DataConstants::orderJoin),
	PRODUCT(DataConstants::productsColumns),
	ORDER_DETAIL(DataConstants::ordersDetailsColumns),
	EMPLOYEE(DataConstants::employeesColumns),
	;

	private final Function<JQDemoColumn, String> colMap;
	private final Function<String, JoinBuilder> joins;
	
	private JQDemoTable(Function<JQDemoColumn, String> colMap) {
		this.colMap = colMap;
		this.joins = null;
	}
	
	@Override
	public String identity() {
		return name().toLowerCase();
	}

	@Override
	public CriteriaBuilder<DBFilter> criteria(String name) { //TODO split
		return ViewDecorator.super.criteria(name);
	}

	@Override
	public String columnName(ColumnDecorator cd) {
		return colMap.apply((JQDemoColumn) cd);
	}
	@Override
	public JoinBuilder join(String name) {
		return joins == null ? ViewDecorator.super.join(name) : joins.apply(name);
	}

}
