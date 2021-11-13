``SpringBoot Docker NGINX`` is a simple SpringBoot app that uses the [NGINX](https://www.nginx.com/) as load balancer.

---
# Error Handling for REST with Spring

Let's show how to implement Exception Handling with Spring for a REST API.

Now Spring 5 introduces the `ResponseStatusException` class — a fast way for basic error handling in our REST APIs.

All of these do have one thing in common: They deal with the separation of concerns very well. The app can throw exceptions normally to indicate a failure of some kind, which will then be handled separately.

## Solution 1: the Controller-Level `@ExceptionHandler`

The first solution works at the `@Controller` level. We will define a method to handle exceptions and annotate that with `@ExceptionHandler`:

```java
public class FooController{

    //...
    @ExceptionHandler({ CustomException1.class, CustomException2.class })
    public void handleException() {
        //
    }
}
```

This approach has a major drawback: 
- The @ExceptionHandler annotated method is only active for that particular Controller, not globally for the entire application. Of course, adding this to every controller makes it not well suited for a general exception handling mechanism.

We can work around this limitation by having all Controllers extend a Base Controller class.

However, this solution can be a problem for applications where, for whatever reason, that isn't possible. For example, the Controllers may already extend from another base class, which may be in another jar or not directly modifiable, or may themselves not be directly modifiable.

## Solution 2: the HandlerExceptionResolver
The second solution is to define an `HandlerExceptionResolver`. This will resolve any exception thrown by the application. 
- It will also allow us to implement a uniform exception handling mechanism in our REST API.

Before going for a custom resolver, let's go over the existing implementations.

### ExceptionHandlerExceptionResolver
This resolver was introduced in Spring 3.1 and is enabled by default in the DispatcherServlet. This is actually the core component of how the @ExceptionHandler mechanism presented earlier works.

### DefaultHandlerExceptionResolver
This resolver is enabled by default in the DispatcherServlet.
- resolve standard Spring exceptions to their corresponding HTTP Status Codes
- Here's the [full list](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/mvc.html#mvc-ann-rest-spring-mvc-exceptions) of the Spring Exceptions it handles and how they map to status codes

One limitation is that it doesn't set anything to the body of the Response
- For a REST API the response has to have a body as well, to allow the application to give additional information about the failure

### ResponseStatusExceptionResolver
This resolver is enabled by default in the `DispatcherServlet`:
- use the `@ResponseStatus` annotation available on custom exceptions
- map these exceptions to HTTP status codes

Such a custom exception may look like:

```java
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MyResourceNotFoundException extends RuntimeException {
    public MyResourceNotFoundException() {
        super();
    }
    public MyResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public MyResourceNotFoundException(String message) {
        super(message);
    }
    public MyResourceNotFoundException(Throwable cause) {
        super(cause);
    }
}
```

The same as the `DefaultHandlerExceptionResolver`, it does map the Status Code on the response, but the body is still null.

### Solution 4: ResponseStatusException
Spring 5 introduced the `ResponseStatusException` class.

We can create an instance of it providing an HttpStatus and optionally a reason and a cause:

```java
@GetMapping(value = "/{id}")
public Foo findById(@PathVariable("id") Long id, HttpServletResponse response) {
    try {
        Foo resourceById = RestPreconditions.checkFound(service.findOne(id));

        eventPublisher.publishEvent(new SingleResourceRetrievedEvent(this, response));
        return resourceById;
     }
    catch (MyResourceNotFoundException exc) {
         throw new ResponseStatusException(
           HttpStatus.NOT_FOUND, "Foo Not Found", exc);
    }
}
```