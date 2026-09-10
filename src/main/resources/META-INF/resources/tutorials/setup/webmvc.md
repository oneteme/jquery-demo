### WebMvcConfig guide

A Resolver converts HTTP request parameters into a `QueryComposer` object.  
Once registered in Spring MVC, it allows you to inject a ready-to-use query directly into your controller methods.

In this guide, you will learn how to:

- Register the Query Resolver in your Spring application
- Configure the required datasources
- Use `MvcRequest` directly in your controllers
- Automatically build queries from HTTP request parameters

1. Register the Resolver

Add the Query Resolver to your Spring MVC configuration.

The resolver needs to be registered in your `WebMvcConfig` class using `addArgumentResolvers`.

```java
// WebMvcConfig.java

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final DataSource ds;
    // Add other datasources for each database used in your project

    @EventListener(ApplicationStartedEvent.class)
    void onReady() {
        getInstance().register(DemoStore.class, ds, /*Optional : Dialect*/ new H2Dialect() );

        // Register other datasources
    }
}

```

The resolver is now available to Spring MVC and can automatically resolve MvcRequest parameters in your controllers.

2. Use the Resolver in a Controller

Once the resolver is registered, you can inject MvcRequest directly into your controller methods.

```java
// MyController.java

@GetMapping("products")
@QueryTemplate(
    dataset = "products",
    select = "id,name,supp_id,cat_id,price,unit"
)
public Object fetchProducts(MvcRequest mvc) {
    return mvc.execute();
}
```

The resolver automatically reads the HTTP request parameters, applies the `@QueryTemplate` configuration, and creates the corresponding query.

<b>Result</b>

Your application can now build dynamic queries directly from HTTP requests.

- Resolvers handle the conversion between HTTP parameters and QueryComposer.
- You only need to register them once.
- After registration, they can be used in any controller.

✅ Your project is now ready to use MvcRequest with QueryTemplate.