package io.github.oneteme.jquery.demo.repo;

import org.usf.jquery.core.ViewColumn;
import org.usf.jquery.web.proxy.Bind;
import org.usf.jquery.web.proxy.DatasetResource;

public interface Products extends DatasetResource {
	
	@Bind("PRODUCT_ID")
	ViewColumn id();
	
	@Bind("PRODUCT_NAME")
	ViewColumn name();
	
	@Bind("SUPPLIER_ID")
	ViewColumn supp_id();
	
	@Bind("CATEGORY_ID")
	ViewColumn cat_id();
	
	@Bind("PRICE")
	ViewColumn price();

	@Bind("UNIT")
	ViewColumn unit();
	
	//par produit je veux comparer le prix avec la moyen des prix de categorie
}
