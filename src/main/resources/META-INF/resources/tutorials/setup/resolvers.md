A Resolver is responsible for converting HTTP request parameters into a query object that can be used directly inside your Spring MVC controllers.

By creating a custom `HandlerMethodArgumentResolver`, you can automatically build an `MvcRequest` from the incoming request and inject it as a controller method parameter.

In this guide, you will learn how to:

- Create a custom Query Resolver
- Register it in your Spring MVC configuration
- Use `MvcRequest` directly in your controllers

1. Create the Query Resolver

Create a new class in your project (for example, `CommonRequestQueryResolver.java`) and make it implement `HandlerMethodArgumentResolver` and `QueryInterpreter`.

This resolver will:

- Detect controller parameters annotated with `@QueryTemplate`
- Read query parameters from the HTTP request
- Apply query template configuration
- Build and return an `MvcRequest` instance

Add the following class to your project:

```java
// CommonRequestQueryResolver.java

public class CommonRequestQueryResolver implements HandlerMethodArgumentResolver, QueryInterpreter {
	
	private static Set<String> NATIVE_PARAMETERS = Set.of(
			CTE_PARAM, SELECT_PARAM, JOIN_PARAM, ORDER_PARAM, 
			DISTINCT_PARAM, LIMIT_PARAM, OFFSET_PARAM, VIEW_PARAM);

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasMethodAnnotation(QueryTemplate.class) 
				&& parameter.getNestedParameterType() == MvcRequest.class;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {		
		var ann = parameter.getMethodAnnotation(QueryTemplate.class);
		if(nonNull(ann)) {
			if(parameter.getNestedParameterType() == MvcRequest.class) {
				return cacheAttribute(webRequest, MvcRequest.class, 
						()-> resolveQueryComposer(ann, parameter, webRequest));
			}
			throw new IllegalStateException("unsupported parameter type: " + parameter.getNestedParameterType());
		}
		throw new IllegalStateException("missing @QueryRequest annotation");
	}

	MvcRequest resolveQueryComposer(QueryTemplate ann, MethodParameter parameter, NativeWebRequest webRequest) {
		var str = resolveStore(ann, parameter);
		var map = new LinkedHashMap<>(webRequest.getParameterMap()); //modifiable map + preserve order
		if(!isEmpty(ann.ignore())) {
			for(var k : ann.ignore()) {
				if(map.containsKey(k)) {
					log.debug("ignoring parameter '{}'", k);
					map.remove(k);
				}
			}
		}
		resolveParameterCompatibility(map);		
		mergeParameters(ann, parameter.getMethodAnnotation(QueryExtension.class), map);
		return parseQuery(str, ann.dataset(), map);
	}
	
	static void mergeParameters(QueryTemplate req, QueryExtension modifier, Map<String, String[]> parameters) {
		if(nonNull(modifier)) {
			resolveParameterValues(CTE_PARAM, modifier.cte(), parameters, req.cte());
			resolveParameterValues(SELECT_PARAM, modifier.select(), parameters, req.select());
			resolveParameterValues(JOIN_PARAM, modifier.join(), parameters, req.join());
			resolveParameterValues(ORDER_PARAM, modifier.order(), parameters, req.order());
			resolveParameterValues(DISTINCT_PARAM, modifier.overrideDistinct(), parameters, req.distinct(), v-> v);
			resolveParameterValues(LIMIT_PARAM, modifier.overrideLimit(), parameters, req.limit(), v-> v > 0);
			resolveParameterValues(OFFSET_PARAM, modifier.overrideOffset(), parameters, req.offset(), v-> v > 0);
			resolveParameterValues(VIEW_PARAM, modifier.overrideView(), parameters, req.view(), s-> !s.isEmpty());
			if(!modifier.acceptCriteria()) {
				for(var v : parameters.keySet()) {
					if(NATIVE_PARAMETERS.contains(v)) {
						throw new IllegalArgumentException("parameter '" + v + "' is not allowed");
					}
				}
			}
		}
		else {
			resolveParameterValues(CTE_PARAM, REPLACE, parameters, req.cte());
			resolveParameterValues(SELECT_PARAM, REPLACE, parameters, req.select());
			resolveParameterValues(JOIN_PARAM, REPLACE, parameters, req.join());
			resolveParameterValues(ORDER_PARAM, REPLACE, parameters, req.order());
			resolveParameterValues(DISTINCT_PARAM, true, parameters, req.distinct(), v-> v);
			resolveParameterValues(LIMIT_PARAM, true, parameters, req.limit(), v-> v > 0);
			resolveParameterValues(OFFSET_PARAM, true, parameters, req.offset(), v-> v > 0);
			resolveParameterValues(VIEW_PARAM, true, parameters, req.view(), s-> !s.isEmpty());
		}
	}
	
	static void resolveParameterValues(String key, Modifier modifier, Map<String, String[]> parameters, String[] values) {
		var arr = parameters.get(key); 
		if(modifier == MERGE) {
			if(nonNull(arr) && !isEmpty(values)) {
				arr = concat(stream(values), stream(arr)).toArray(String[]::new);
			}
			else if(!isEmpty(values)) {
				arr = values;
			}
		}
		else if(modifier == REPLACE) {
			if(isNull(arr) && !isEmpty(values)) {
				arr = values;
			}
		}
		else if(isNull(arr)) {
			if(!isEmpty(values)) {
				arr = values;
			}
		}
		else { // REJECT 
			throw new IllegalArgumentException("parameter '" + key + "' is already defined and cannot be overridden");
		}
		if(nonNull(arr)) {
			parameters.put(key, arr);
		}
	}
	
	static  <T> void resolveParameterValues(String key, boolean override, Map<String, String[]> parameters, T value, Predicate<T> pr) {
		var arr = parameters.get(key);
		if(!override && nonNull(arr)) {
			throw new IllegalArgumentException("parameter '" + key + "' is already defined and cannot be overridden");
		}
		if(isNull(arr) && pr.test(value)) {
			log.debug("setting parameter '{}' with value {}", key, value);
			arr = new String[] { String.valueOf(value) };
		}
		if(nonNull(arr)) {
			parameters.put(key, arr);
		}
	}

	StoreCatalog resolveStore(QueryTemplate ann, MethodParameter parameter) {
		var store = ann.store() == StoreCatalog.class 
				? getInstance().getDefaultStore() 
				: getInstance().getStore(ann.store());
		var rst = parameter.getMethodAnnotation(QueryGuard.class);
		return nonNull(rst) 
				? restrict(store, rst.maxCols(), rst.maxRows(), rst.aggregate(), 
						Set.of(rst.excludeResources()), Set.of(rst.excludeDialects()))
				: store;
	}

	private static <T> T cacheAttribute(NativeWebRequest webRequest, Class<T> clazz, Supplier<? extends T> supplier) {
		var att = webRequest.getAttribute(clazz.getName(), 0);
		if(isNull(att)) {
			att = supplier.get();
			webRequest.setAttribute(clazz.getName(), att, 0);
		}
		return clazz.cast(att);
	}

	private static void resolveParameterCompatibility(Map<String, String[]> modifiableMap) {
		Map.of("column", SELECT_PARAM).entrySet().forEach(e-> {
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

```