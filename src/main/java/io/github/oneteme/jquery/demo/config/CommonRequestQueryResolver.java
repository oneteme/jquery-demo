package io.github.oneteme.jquery.demo.config;

import static java.util.Arrays.stream;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Stream.concat;
import static org.usf.jquery.core.Utils.isEmpty;
import static org.usf.jquery.web.Parameters.COLUMN_PARAM;
import static org.usf.jquery.web.Parameters.CRITERIA_OPR;
import static org.usf.jquery.web.Parameters.CTE_PARAM;
import static org.usf.jquery.web.Parameters.FILTER_OPR;
import static org.usf.jquery.web.Parameters.SELECT_PARAM;
import static org.usf.jquery.web.Parameters.VIEW_PARAM;
import static org.usf.jquery.web.proxy.RestrictedStore.restrict;
import static org.usf.jquery.web.proxy.StoreManager.getInstance;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.usf.jquery.core.QueryComposer;
import org.usf.jquery.web.proxy.QueryInterpreter;
import org.usf.jquery.web.proxy.QueryRequest;
import org.usf.jquery.web.proxy.Restriction;
import org.usf.jquery.web.proxy.StoreResource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        if(isNull(ann)) {
            throw new IllegalStateException("missing @QueryRequest annotation");
		}
    	var store = ann.store() == StoreResource.class 
    			? getInstance().getDefaultStore() 
    			: getInstance().getStore(ann.store());
        var rst = parameter.getParameterAnnotation(Restriction.class);
		if(nonNull(rst)) {
			store = restrict(store, rst.maxCols(), rst.maxRows(), rst.aggregate(), 
					Set.of(rst.excludeResources()), Set.of(rst.excludeDialects()));
		}
		var modifiableMap = new LinkedHashMap<>(webRequest.getParameterMap()); //modifiable map + preserve order
		if(!isEmpty(ann.ignore())) {
			for(var k : ann.ignore()) {
				if(modifiableMap.containsKey(k)) {
					log.debug("ignoring parameter '{}' as specified in @QueryRequest", k);
					modifiableMap.remove(k);
				}
			}
		}
		resolveParameterCompatibility(modifiableMap);
		modifiableMap.computeIfAbsent(SELECT_PARAM, k-> ann.fields());	
    	return parseQuery(store, ann.dataset(), modifiableMap);
    }
    
	private static void resolveParameterCompatibility(Map<String, String[]> modifiableMap) {
		Map.of(COLUMN_PARAM, SELECT_PARAM, FILTER_OPR, CRITERIA_OPR, VIEW_PARAM, CTE_PARAM).entrySet().forEach(e-> {
			var args = modifiableMap.remove(e.getKey());
			if(!isEmpty(args)) {
				log.warn("'{}' parameter is deprecated, use {} instead", e.getKey(), e.getValue());
				modifiableMap.compute(e.getValue(), (k, v)-> isEmpty(v) 
						? args 
						: concat(stream(v), stream(args)).toArray(String[]::new));
			}
		});
	}
}

