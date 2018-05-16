

## Injecting Application Context

Many components need the application context to resolve locales, resources, etc.

App context is easily reachable from a standard lifecycle component such as App itself, Activities or Fragments.
Other components may need to access it in a more custom way.

Using Dagger, we can define a new annotation and a module provider. Then any component requiring this app context simply has to typehint a parameter in its constructor (and obviously require dagger injection via @Inject annotation).

```
// outerspacemanager/di/annotation/AppContext.java

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface AppContext {}
```