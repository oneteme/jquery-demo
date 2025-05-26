package io.github.oneteme.jquery.demo;

import java.util.Objects;

import org.usf.jquery.core.ComparisonExpression;
import org.usf.jquery.core.DBColumn;
import org.usf.jquery.web.Builder;
import org.usf.jquery.web.ColumnDecorator;
import org.usf.jquery.web.ViewDecorator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum JQDemoColumn implements ColumnDecorator {
	ID("id"),
	NAME("name"),
	START("start"),
	FILTER("filter"),
	TABLE_NAME("table_name"),
	CUSTOMER("customer"),
	CONTACT("contact"),
	ADDRESS("address"),
	CITY("city"),
	POSTAL_CODE("postal_code"),
	COUNTRY("country"),
	LAST_NAME("last_name"),
	PHOTO("photo"),
	NOTES("notes"),
	DESCRIPTION("description"),
	PHONE("phone"),
	UNIT("unit"),
	PRICE("price"),
	QUANTITY("quantity"),
	CUSTOMER_ID("customer_id"),
	EMPLOYEE_ID("employee_id"),
	SHIPPER_ID("shipper_id"),
	SUPPLIER_ID("supplier_id"),
	CATEGORY_ID("category_id"),
	PRODUCT_ID("category_id"),
	ORDER_ID("order_id"),
	DEFINITION("definition"),
	SYNTAX("syntax"),
	;

	private final String reference;
	private final Builder<ViewDecorator, DBColumn> builder;
	private final Builder<ViewDecorator, ComparisonExpression> crBulder;
	
	JQDemoColumn(@NonNull String ref)
	{
		this(ref, null, null);
	}
	
	JQDemoColumn(@NonNull String ref, @NonNull Builder<ViewDecorator, DBColumn> builder) {
        this(ref, builder, null);
    }

	@Override
	public String identity() {
		return this.name().toLowerCase();
	}

	@Override
	public String reference(ViewDecorator vd) {
		return reference;
	}

	@Override
	public Builder<ViewDecorator, DBColumn> builder() {
		return Objects.nonNull(builder) ? builder : ColumnDecorator.super.builder();
	}

	@Override
	public Builder<ViewDecorator, ComparisonExpression> criteria(String name) {
		return "ym".equals(name) && Objects.nonNull(crBulder) ? crBulder : null;
	}
}
