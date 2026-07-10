package io.github.oneteme.jquery.demo;

import static org.usf.jquery.mvc.StoreManager.getInstance;

import javax.sql.DataSource;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.github.oneteme.jquery.demo.repo.DemoStore;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebmvcConfig implements WebMvcConfigurer {

    private final DataSource ds;

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//    	registry.addResourceHandler("/resources/**")
//    	.addResourceLocations("/resources/");
//    }
    
    @EventListener(ApplicationStartedEvent.class)
    void onReady() {
    	getInstance().register(DemoStore.class, ds);
    }
}
