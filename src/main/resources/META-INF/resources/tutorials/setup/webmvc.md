1. CommonRequestQueryResolver

To be able to write queries in the URL we will need HandlerMethodArgumentResolver.

Let's create a HandlerMethodArgumentResolver class called "CommonRequestQueryResolver"

```java
// CommonRequestQueryResolver.java

public class CommonRequestQueryResolver implements HandlerMethodArgumentResolver {


}

// CommonRequestQueryResolver.java
```

2. Setup CommonRequestQueryResolver

```java
// CommonRequestQueryResolver.java

public class CommonRequestQueryResolver implements HandlerMethodArgumentResolver {

    private final RequestParameterResolver resolver = new RequestParameterResolver();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return QueryBuilder.class.isAssignableFrom(parameter.getNestedParameterType())
                && parameter.hasParameterAnnotation(RequestQueryParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        var crp = parameter.getParameterAnnotation(RequestQueryParam.class);
        return resolver.requestQuery(crp, webRequest.getParameterMap());
    }
}

// CommonRequestQueryResolver.java
```

3. WebMvcConfig Setup

And finally we create the "webMvcConfig" class in order to load our tables,columns and databases

```java
// WebMvcConfig.java

@Configuration
@RequiredArgsConstructor
public class WebmvcConfig implements WebMvcConfigurer {
    
    private final DataSource ds;

	@Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        register(ContextEnvironment.of(
    			DEMO,
    			asList(JQDemoTable.values()),
        		asList(JQDemoColumn.values()), ds));
        resolvers.add(new CommonRequestQueryResolver());
    }
}

```
