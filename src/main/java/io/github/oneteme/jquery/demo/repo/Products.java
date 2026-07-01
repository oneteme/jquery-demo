package io.github.oneteme.jquery.demo.repo;

import static org.usf.jquery.core.Column.beginCase;
import static org.usf.jquery.core.Join.innerJoin;
import static org.usf.jquery.core.JoinGroup.joins;
import static org.usf.jquery.core.Predicate.ge;
import static org.usf.jquery.core.Predicate.lt;
import static org.usf.jquery.mvc.StoreManager.getInstance;

import org.usf.jquery.core.CaseColumn;
import org.usf.jquery.core.Column;
import org.usf.jquery.core.Criteria;
import org.usf.jquery.core.JoinGroup;
import org.usf.jquery.core.Predicate;
import org.usf.jquery.core.ViewColumn;
import org.usf.jquery.mvc.Bind;
import org.usf.jquery.mvc.DatasetResource;
import org.usf.jquery.mvc.Expose;

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
	
	default Criteria critColumn(String unit) {
		return unit().eq(unit);
	}
	
	default CaseColumn whenCol() {
		return price().toCase().when(lt(10), "Cheap").when(ge(10).and(lt(20)), "Normal").orElse("Expensive");
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

}
