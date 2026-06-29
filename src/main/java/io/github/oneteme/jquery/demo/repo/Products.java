package io.github.oneteme.jquery.demo.repo;

import static org.usf.jquery.core.Column.beginCase;
import static org.usf.jquery.core.Join.innerJoin;
import static org.usf.jquery.core.JoinGroup.joins;
import static org.usf.jquery.core.Predicate.lt;
import static org.usf.jquery.web.proxy.StoreManager.getInstance;

import org.usf.jquery.core.CaseColumn;
import org.usf.jquery.core.Column;
import org.usf.jquery.core.Criteria;
import org.usf.jquery.core.JoinGroup;
import org.usf.jquery.core.ViewColumn;
import org.usf.jquery.web.proxy.Bind;
import org.usf.jquery.web.proxy.DatasetResource;
import org.usf.jquery.web.proxy.Expose;

public interface Products extends DatasetResource {
	
	@Bind("PRODUCT_ID")
	ViewColumn id();
	
	@Bind("PRODUCT_NAME")
	ViewColumn name();
	
	@Expose(identity="supp_id")
	@Bind("SUPPLIER_ID")
	ViewColumn suppId();
	
	@Expose(identity="cat_id")
	@Bind("CATEGORY_ID")
	ViewColumn catId();
	
	@Bind("PRICE")
	ViewColumn price();

	@Bind("UNIT")
	ViewColumn unit();
	
	default Column powPrice() {
		return price().pow(2);
	}

	default Column priceABS() {
		return price().abs();
	}
	
	default Criteria critColumn(String unit) {
		return unit().eq(unit);
	}
	
	default Criteria inCol(Integer ...arr) {
		return price().in(arr);
	}
	
	default Criteria startsLike(String pattern) {
		return price().startsLike(pattern);
	}
	
	default CaseColumn whenCol() {
		return price().toCase().when(lt(10), "cheap").orElse("expensive");
	}
	
	default CaseColumn whenColCase() {
		return beginCase().when(price().lt(10), "cheap").orElse("Expensive");
	}
	
	default Column columnYear() {
		return price().year();
	}
	default JoinGroup innerCat() {
		var cat = getInstance().getStore(DemoStore.class).categories();
		return joins(innerJoin(cat.getView(), catId().eq(cat.id())));
	}
	
	default Column getPi() {
		return getInstance().getStore(DemoStore.class).pi().invoke();
	}
	
	default Column getPercentileCont() {
		return price().bitNot();
	}
//	default Column columnRank() {
//		return Dialect.getDialect().rank();
//	}
	
	
	//par product je veux comparer le prix avec la moyen des prix de categorie
}
