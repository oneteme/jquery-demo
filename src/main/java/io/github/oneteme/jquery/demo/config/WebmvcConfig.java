package io.github.oneteme.jquery.demo.config;

import static io.github.oneteme.jquery.demo.JQDatabase.DEMO;
import static java.util.Arrays.asList;
import static org.usf.jquery.web.ContextManager.register;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.usf.jquery.web.ContextEnvironment;

import io.github.oneteme.jquery.demo.JQDemoColumn;
import io.github.oneteme.jquery.demo.JQDemoTable;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebmvcConfig implements WebMvcConfigurer {

    private final DataSource ds;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new CommonRequestQueryResolver());
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("/resources/**")
    	.addResourceLocations("/resources/");
    }
    
    @EventListener(ApplicationStartedEvent.class)
    void onReady() {
        register(ContextEnvironment.of(
    			DEMO,
    			asList(JQDemoTable.values()),
        		asList(JQDemoColumn.values()), ds));
    	
    }
}
