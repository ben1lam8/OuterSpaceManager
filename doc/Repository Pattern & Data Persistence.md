# Repository Pattern & Data Persistence

## Content
1. [Dependencies](#Dependencies)
2. [Repository Pattern](#Repository%20Pattern)
3. [Observable LiveData](#Observable%20LiveData)
4. [Refreshing UIs](#Refreshing%20UIs)

## Dependencies

```
    implementation "android.arch.lifecycle:livedata:1.1.1"
    implementation "android.arch.persistence.room:runtime:1.0.0"
    implementation "android.arch.paging:runtime:1.0.0-rc1"
    implementation 'com.squareup.retrofit2:retrofit:2.2.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.2.0'
    implementation 'com.google.code.gson:gson:2.8.0' 
  
    annotationProcessor "android.arch.lifecycle:compiler:1.1.1"
  
    testImplementation "android.arch.persistence.room:testing:1.0.0"
```

## Repository Pattern

![Repository Pattern](images/repository1.jpeg?raw=true "Repository Pattern")
![Repository Pattern](images/repository2.png?raw=true "Repository Pattern")

A repository is a layer exposing data coming from multiple sources. Its job is to synchronize all these sources and to expose only consolidated data containers to VMs.

## Observable LiveData

Here, these containers are LiveData : observable containers which VMs activities can subscribe to and update their UI when a change in this data has been notified.

When using both API (Retrofit) and local DB (Room) sources, we let the repository to use Room and create the initial LiveData that will be updated with any persisted data change.
Then, the repo asks the API for an up-to-date data that will be persisted into DB and rise a change notification through the corresponding observables.

Every observable listeners receive this notification and re-read the data value to update their UI. 

## Refreshing UIs

In this application, user entity stores a token that is used to fetch other datas. Every UI has to refresh its user data to stay up-to-date.
Thus, activities/fragments can observe the user data to trig other refresh actions : when a data change is notified through the user LiveData, the other refreshing methods can be called.

```
// outerspacemanager/ui/fragment/SearchesFragment.java  
  
public class SearchesFragment extends Fragment {
    ...

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.vm.getUser().observe(
                        this,
                        user -> { if (user != null) refresh();}
        );
        
        this.vm.getSearches().observe(
                        this,
                        searches -> { if (searches != null && searches.size() > 0) updateUI(searches); }
        );

    }

    private void updateUI(List<Search> searches){
        this.searchesListItemAdapter.setObjects(searches);
        Timber.d("Updated listview objects");
    }

    private void refresh(){
        this.vm.refreshSearches();
        Timber.d("Refreshing binded data...");
    }
}
```

We can now add a menu option inside the MainActivity to trig the initial user refresh. Every fragment inside the MainActivity observing the user data will then be updated and refresh its other binded datas.

```
// outerspacemanager/ui/actiity/MainActivity.java  
  
public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    ...

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_main_refresh:
                this.vm.refreshUserStats();
                return true;
            ...
            default:
                return false;
        }
    }
}
```

```
// res/menu/menu_main.xml  
  
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:android="http://schemas.android.com/apk/res/android" >
    <item
        android:id="@+id/menu_main_refresh"
        android:title="@string/menu_main_refresh"
        app:showAsAction="ifRoom" />
    ...
</menu>
```