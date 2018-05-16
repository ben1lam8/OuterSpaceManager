# MVVM Architecture

## Content
1. Dependencies
2. Pattern
3. Share VMs between an Activity and its Fragment

## Dependencies

```
    implementation "android.arch.lifecycle:extensions:1.1.1"
    implementation "android.arch.lifecycle:viewmodel:1.1.1"
    implementation "android.arch.lifecycle:livedata:1.1.1"
    implementation "android.arch.persistence.room:runtime:1.0.0"
    implementation 'com.android.support:support-v4:27.1.1'  
  
    annotationProcessor "android.arch.lifecycle:compiler:1.1.1"
    annotationProcessor "android.arch.persistence.room:compiler:1.0.0"  
  
    testImplementation "android.arch.core:core-testing:1.1.1"
    testImplementation "android.arch.persistence.room:testing:1.0.0"
```

## Pattern

The MVVM Pattern aims to expose only useful data to activities and fragments.

![MVVM Pattern](images/mvvm1.png?raw=true "MVVM Pattern")

In a MVVM Pattern, the activity/fragment role is to manage UI events and read/write the data exposed by its ViewModel.

When moving data scope outside the activity/fragment, we also let it survives configuration changes.

## Share VMs between an Activity and its Fragment

Considering VMs as app-wide singletons (thanks to the VM provider), it is now possible to share the same VM instance between an activity and one of its fragments.
We don't need any "reverse listener" anymore : to send data from a fragment to its activity, simply the value inside the shared VM from the fragment, then read it from the activity !

```
// outerspacemanager/viewmodel/MainViewModel.java

public class MainViewModel extends ViewModel{

    ...

    private MutableLiveData<String> currentDetailFragmentTag;

    ...

    public MutableLiveData<String> getCurrentDetailFragmentTag(){
        return this.currentDetailFragmentTag;
    }

    public void replaceDetailFragment(String tag){
        this.currentDetailFragmentTag.setValue(tag);
    }
}


```

```
// outerspacemanager/ui/activity/MainActivity.java

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    ...

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private MainViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        ...
        
        this.vm = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
        
        this.vm.getCurrentDetailFragmentTag().observe(
                        this,
                        this::showDetailFragment
                );
    }

    private void showDetailFragment(String tag) {

        Fragment detailFragment = getSupportFragmentManager().findFragmentByTag(tag);

        if(detailFragment == null){
            switch (tag){
                case MainViewModel.BUILDING_FRAGMENT_TAG:
                    detailFragment = new BuildingsFragment();
                    break;
                case MainViewModel.SEARCHES_FRAGMENT_TAG:
                    detailFragment = new SearchesFragment();
                    break;
                case MainViewModel.SHIPYARD_FRAGMENT_TAG:
                    detailFragment = new ShipyardFragment();
                    break;
                case MainViewModel.GALAXY_FRAGMENT_TAG:
                    detailFragment = new GalaxyFragment();
                    break;
                default:
                    detailFragment = new BuildingsFragment();
            }
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.detail_container, detailFragment, tag);
        transaction.addToBackStack(null);

        transaction.commit();
    }
    
    ...
}

```

```
// outerspacemanager/ui/activity/MainFragment.java

public class MainFragment extends Fragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private MainViewModel vm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ...
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        vm = ViewModelProviders.of(getActivity(), viewModelFactory).get(MainViewModel.class);
    }


    @OnClick(R.id.main_galaxy_button)
    public void showGalaxyDetail(Button button){
        Timber.d("Click on %s", button.getText());
        this.vm.replaceDetailFragment(MainViewModel.GALAXY_FRAGMENT_TAG);
    }

    ...

}

```


This behaviour is greatly fastened by using LiveData (see "Repository Pattern & Data Persistence").
