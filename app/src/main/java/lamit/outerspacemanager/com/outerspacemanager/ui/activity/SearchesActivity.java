package lamit.outerspacemanager.com.outerspacemanager.ui.activity;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import lamit.outerspacemanager.com.outerspacemanager.R;
import lamit.outerspacemanager.com.outerspacemanager.ui.fragment.SearchesFragment;
import lamit.outerspacemanager.com.outerspacemanager.viewmodel.SearchesViewModel;
import timber.log.Timber;

public class SearchesActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private SearchesViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("Created");

        // Inject dependencies
        this.configureDagger();
        Timber.d("Dependencies injected");

        // Configure VM
        this.configureViewModel();
        Timber.d("ViewModel ready");

        // Inflate layout and bind views
        setContentView(R.layout.activity_main);
        this.showFragment(savedInstanceState);
        Timber.d("Layout inflated");

        ButterKnife.bind(this);
        Timber.d("Views binded");

        // Now listen...
        Timber.d("Listening to data mutations and UI events...");
    }

    private void configureDagger(){ AndroidInjection.inject(this); }

    private void configureViewModel(){
        this.vm = ViewModelProviders.of(this, viewModelFactory).get(SearchesViewModel.class);
        this.vm.init();

        // Observe data mutations...
    }

    private void showFragment(Bundle savedInstanceState){
        if (savedInstanceState != null) return;

        SearchesFragment searchesFragment = new SearchesFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.searches_container, searchesFragment, null)
                .commit();
    }
}
