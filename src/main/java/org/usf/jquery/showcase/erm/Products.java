package org.usf.jquery.showcase.erm;

import static org.usf.jquery.core.Column.beginCase;
import static org.usf.jquery.core.Column.denseRank;
import static org.usf.jquery.core.Column.rank;
import static org.usf.jquery.core.Column.rowNumber;
import static org.usf.jquery.core.Join.innerJoin;
import static org.usf.jquery.core.JoinGroup.joins;
import static org.usf.jquery.core.Predicate.ge;
import static org.usf.jquery.core.Predicate.lt;
import static org.usf.jquery.mvc.StoreManager.getInstance;

import org.usf.jquery.core.CaseColumn;
import org.usf.jquery.core.Column;
import org.usf.jquery.core.Criteria;
import org.usf.jquery.core.JoinGroup;
import org.usf.jquery.core.Partition;
import org.usf.jquery.core.PartitionComposer;
import org.usf.jquery.core.Predicate;
import org.usf.jquery.core.QueryComposer;
import org.usf.jquery.core.SingleQueryColumn;
import org.usf.jquery.core.ViewColumn;
import org.usf.jquery.mvc.Bind;
import org.usf.jquery.mvc.DatasetCatalog;
import org.usf.jquery.mvc.Expose;

public interface Products extends DatasetCatalog<DemoStore>,CommunColumns {
	
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
	
	default Criteria nameRangeByPrice(String name, Integer v1, Integer v2) {
		return price().between(v1, v2).and(name().contentLike(name));
	}
	
	default Criteria expensiveProduct() {
		return pricePredicate(Predicate.gt(40));
	}
	
	default Criteria pricePredicate(Predicate pred) {
		return price().filter(pred);
	}
	
	default CaseColumn priceToCase() {
		return price().toCase().when(lt(10), "Cheap").when(ge(10).and(lt(20)), "Normal").orElse("Expensive");
	}
	
	default CaseColumn priceWhen() {
		return beginCase().when(price().lt(10), "Cheap").when(price().ge(10).and(price().lt(20)), "Normal").orElse("Expensive");
	}

	default JoinGroup innerCat() {
		var cat = currentStore().categories();
		return joins(innerJoin(cat.getView(), catId().eq(cat.id())));
	}

	
	default Partition partitionByCategoryPrice() {
		return new PartitionComposer()
				.columns(catId())
				.orders(price().desc())
				.compose(currentStore());
	}
	
	default Column rankProducts() {
		return rank().over(partitionByCategoryPrice());
	}
	
	default Column denseProducts() {
		return denseRank().over(partitionByCategoryPrice());
	}
	
	default Column rowProducts() {
		return rowNumber().over(partitionByCategoryPrice());
	}
	
	default SingleQueryColumn single() {
		return new QueryComposer()
				.columns(id())
				.criteria(name().startsLike("Con"))
				.compose(currentStore()).asColumn();
	}
	
//	default Column percentRankProducts(ViewColumn partition, Order order) {
//		return percentRank().over(new PartitionComposer().columns(partition).orders(order).compose(getInstance().getStore(DemoStore.class)));
//	}
}
