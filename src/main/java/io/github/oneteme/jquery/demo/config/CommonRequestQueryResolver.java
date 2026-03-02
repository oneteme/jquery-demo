package io.github.oneteme.jquery.demo.config;

import static java.util.Objects.nonNull;
import static org.usf.jquery.web.proxy.JQueryManager.getDefaultSchema;
import static org.usf.jquery.web.proxy.JQueryManager.getSchema;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.usf.jquery.core.QueryComposer;
import org.usf.jquery.web.proxy.QueryRequest;
import org.usf.jquery.web.proxy.RequestQueryMapper;
import org.usf.jquery.web.proxy.SchemaResource;

public class CommonRequestQueryResolver implements HandlerMethodArgumentResolver, RequestQueryMapper {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return QueryComposer.class.isAssignableFrom(parameter.getNestedParameterType())
                && parameter.hasParameterAnnotation(QueryRequest.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        var qr = parameter.getParameterAnnotation(QueryRequest.class);
        if(nonNull(qr)) {
        	var schema = qr.database() == SchemaResource.class 
        			? getDefaultSchema() 
        			: getSchema(qr.database());
        	var mapper = schema instanceof RequestQueryMapper m ? m : this;
        	return mapper.requestQuery(qr, webRequest.getParameterMap());
		}
        throw new IllegalStateException("missing @QueryRequest annotation");
    }
}

