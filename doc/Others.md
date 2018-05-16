# Others

## Content
1. GSon Filter Injection

## GSon Adapter Injection

GSon provides a Builder with methods to customize the service.

Amongst other possibilities, we can register adapters to automatically deserialize JSON into non-primitive and non-model variables, such as Date.
(Here, the API returns 2 different timestamp formats)

```
// outerspacemanager/di/module/AppModule.java  
  
@Provides
Gson provideGson() {
    return new GsonBuilder()
        .registerTypeAdapter(
            Date.class,
            (JsonDeserializer<Date>) (json, typeOfT, context) -> {

                if (json.getAsString().length() == 13){
                    return new Date(json.getAsJsonPrimitive().getAsLong());
                }else{
                    return new Date(json.getAsJsonPrimitive().getAsLong()*1000);
                }
            })
        .create();
}
```

## EventBus

Many datas are processed outside activities/fragments. To display them, we have to send them to the UI thread. A way to do so is to create a common dispatcher, register the aimed activity as a subscriber, then emit messages from anywhere in the app.

Custom messages will be sent to the "event bus" and corresponding subscribers callbacks will be triggered.

```
// outerspacemanager/repository/BuildingRepository.java
  
public class BuildingRepository {

    ...

    public void refreshBuildings(String token){
        executor.execute(() -> {

            apiClient.fetchBuildingsList(token).enqueue(new Callback<BuildingsList>() {

                @Override
                public void onResponse(Call<BuildingsList> call, Response<BuildingsList> response) {

                    if (response.isSuccessful()){

                        executor.execute(() -> {
                            ...

                            EventBus
                                    .getDefault()
                                    .post(new RepositoryMessageEvent(
                                            appContext.getString(R.string.toast_confirm_fetch_buildings),
                                            Toast.LENGTH_SHORT)
                                    )
                            ;
                        });
                    }
                    ...
                }
            });
        });
    }
}
```

```
// outerspacemanager/event/RepositoryMessageEvent.java

public class RepositoryMessageEvent {

    private final String message;
    private final int length;

    public RepositoryMessageEvent(String message, int length){
        this.message = message;
        this.length = length;
    }

    public String getMessage() {
        return message;
    }

    public int getLength() {
        return length;
    }
}
```

```
// outerspacemanager/ui/activity/MainActivity.java
  
@Subscribe(threadMode = ThreadMode.MAIN)
public void onRepositoryMessageEvent(RepositoryMessageEvent event) {
    Toast.makeText(this, event.getMessage(), event.getLength()).show();
}
```

## Executors

...