package org.usf.jquery.showcase.erm;

import org.usf.jquery.mvc.DatasetCatalog;

public interface CommunColumns extends DatasetCatalog<DemoStore> {
	default DemoStore currentStore() {
		return getStore();
	}
}
