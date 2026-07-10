package org.usf.jquery.showcase;

import static org.usf.jquery.mvc.StoreManager.getInstance;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.usf.jquery.showcase.erm.H2Store;

@Configuration
public class WebmvcConfig {

    private final DataSource H2Ds;
    private final DataSource postgreDs;

    public WebmvcConfig(
    		@Qualifier("h2DataSource") DataSource H2Ds,
    		@Qualifier("postgreDataSource") DataSource postgreDs) {

		this.H2Ds = H2Ds;
		this.postgreDs = postgreDs;
	}

    @EventListener(ApplicationStartedEvent.class)
    void onReady() {
    	getInstance().register(H2Store.class, H2Ds);
//    	getInstance().register(PostGreStore.class, postgreDs);
    }
}
