A Resolver converts HTTP request parameters into a QueryComposer. It allows you to directly inject a query object into your controller.

In this guide, you will learn how to:

- Create resolvers
- Register them in your project
- Use them in a controller

1. Create the QueryRequest Resolver

```java
// CommonRequestQueryResolver.java

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
        if (ann != null) {
            var schema = ann.store() == StoreResource.class 
                    ? getInstance().getDefaultStore() 
                    : getInstance().getStore(ann.store());

            var mapper = schema instanceof QueryInterpreter m ? m : this;
            return mapper.parseQuery(ann, webRequest.getParameterMap());
        }
        throw new IllegalStateException("missing @QueryRequest annotation");
    }
}
```

2. Create the QueryRequestFilter Resolver

Create a second resolver using the same implementation, and replace:

- QueryRequest → QueryRequestFilter

3. Register the Resolvers

Add both resolvers to your Spring configuration.

```java
// WebMvcConfig.java

public class WebmvcConfig implements WebMvcConfigurer {

    private final DataSource ds;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new CommonRequestQueryResolver());
        resolvers.add(new CommonRequestQueryFilterResolver());
    }

    @EventListener(ApplicationStartedEvent.class)
    void onReady() {
        StoreManager.getInstance().register(DemoStore.class, ds);
    }
}
```

4. Quick Example

Once the resolvers are registered, you can use them in your controller.

```java
// myController.java
@GetMapping("products")
public Map<String, Object> fetchProducts(
    @QueryRequest(dataset = "products", fields = "id,name,price") QueryComposer query) {
    return execute(DemoStore.class, query);
}
```

<b>Result</b>

The request parameters are automatically converted into a query.

- Resolvers convert HTTP requests into QueryComposer
- You need to:
    - Create them
    - Register them
    - Once registered, they can be used directly in controllers

✅ Your project is now ready to use QueryRequest and QueryRequestFilter.
