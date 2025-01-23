package io.github.oneteme.jquery.demo;

import java.util.function.Function;

import org.usf.jquery.web.DatabaseDecorator;
import org.usf.jquery.web.ViewDecorator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum JQDatabase implements DatabaseDecorator {
	DEMO(DBDataConstant::demoViews);
	
	private final Function<ViewDecorator, String> colMap;
	@Override
	public String identity() {
		return name().toLowerCase();
	}

	@Override
	public String viewName(ViewDecorator vd) {
		return colMap.apply(vd);
	}
}
