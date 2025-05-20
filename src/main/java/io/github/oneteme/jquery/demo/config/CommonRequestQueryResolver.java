package io.github.oneteme.jquery.demo.config;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.usf.jquery.core.QueryComposer;
import org.usf.jquery.web.RequestParameterResolver2;
import org.usf.jquery.web.RequestQueryParam2;

public class CommonRequestQueryResolver implements HandlerMethodArgumentResolver {

    private final RequestParameterResolver2 resolver = new RequestParameterResolver2();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return QueryComposer.class.isAssignableFrom(parameter.getNestedParameterType())
                && parameter.hasParameterAnnotation(RequestQueryParam2.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        var crp = parameter.getParameterAnnotation(RequestQueryParam2.class);
        return resolver.requestQueryCheck(crp, webRequest.getParameterMap());
    }
}

