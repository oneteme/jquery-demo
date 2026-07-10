package io.github.oneteme.jquery.demo;

import static org.usf.jquery.mvc.StoreManager.getInstance;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.github.oneteme.jquery.demo.repo.stores.H2Store;
import io.github.oneteme.jquery.demo.repo.stores.PostGreStore;

@Configuration
public class WebmvcConfig implements WebMvcConfigurer {

    private final DataSource H2Ds;
    private final DataSource postgreDs;


    public WebmvcConfig(
    		@Qualifier("h2DataSource") DataSource H2Ds,
    		@Qualifier("postgreDataSource") DataSource postgreDs
    		) {

		this.H2Ds = H2Ds;
		this.postgreDs = postgreDs;
	}

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//    	registry.addResourceHandler("/resources/**")
//    	.addResourceLocations("/resources/");
//    }
    
    @EventListener(ApplicationStartedEvent.class)
    void onReady() {
    	getInstance().register(H2Store.class, H2Ds);
//    	getInstance().register(PostGreStore.class, postgreDs);
    }
}
