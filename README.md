# OUTERSPACEMANAGER

This project is a scholar project, based on the Android course by Raphael Bischof, dispensed to DIM, at IUT Annecy, France.

## Objectives

The goal of this project is to use Android SDK core components and to develop a sample RPG game to test it.

## Progress

This project has been restarted to implement the MVVM architecture. *Many previous features still hasn't been re-implemented with this architecture*.

## Recommended Virtual Device

Layout configuration changes don't work with this app version. *Thus, we recommend to use a large display device, such as Nexus 10*.

A configuration check to manage loaded layouts and injected fragments will be added soon...

## Dependencies

In addition to the base/standard components, the following libraries are used :
* Architecture Components  
    * ViewModel : implements the MVVM pattern
    * LiveData : to use observables
    * Room : to use an android-supported data persistence layer, coupled to LiveData library
* Dagger2 : implements the Dependency Injection pattern
* Retrofit : an HTTP client to forge API requests
* Gson : a (de)serialization library
* Butterknife : views and listeners "injector"
* Glide : image cache handler
* EventBus : app-wide event dispatcher
* Timber : logger

## Documentation

1. [MVVM Architecture](doc/MVVM%20Architecture.md)
2. [Repository Pattern & Data Persistence](doc/Repository%20Pattern%20&%20Data%20Persistence.md)
3. [Dependency Injection](doc/Dependency%20Injection.md)
4. [Others](doc/Others.md)