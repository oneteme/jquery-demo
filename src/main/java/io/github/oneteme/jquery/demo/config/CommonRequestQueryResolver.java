package io.github.oneteme.jquery.demo.config;

import static java.util.Objects.nonNull;
import static org.usf.jquery.web.proxy.StoreManager.getInstance;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.usf.jquery.core.QueryComposer;
import org.usf.jquery.web.proxy.QueryInterpreter;
import org.usf.jquery.web.proxy.QueryRequest;
import org.usf.jquery.web.proxy.StoreResource;

public class CommonRequestQueryResolver implements HandlerMethodArgumentResolver, QueryInterpreter {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return QueryComposer.class.isAssignableFrom(parameter.getNestedParameterType())
                && parameter.hasParameterAnnotation(QueryRequest.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        var ann = parameter.getParameterAnnotation(QueryRequest.class);
        if(nonNull(ann)) {
        	var schema = ann.store() == StoreResource.class 
        			? getInstance().getDefaultStore() 
        			: getInstance().getStore(ann.store());
        	var mapper = schema instanceof QueryInterpreter m ? m : this;
        	return mapper.parseQuery(ann, webRequest.getParameterMap());
		}
        throw new IllegalStateException("missing @QueryRequest annotation");
    }
}

