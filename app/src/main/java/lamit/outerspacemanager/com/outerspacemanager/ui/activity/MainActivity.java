package lamit.outerspacemanager.com.outerspacemanager.ui.activity;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import javax.inject.Inject;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import lamit.outerspacemanager.com.outerspacemanager.R;
import lamit.outerspacemanager.com.outerspacemanager.ui.fragment.BuildingsFragment;
import lamit.outerspacemanager.com.outerspacemanager.ui.fragment.MainFragment;
import lamit.outerspacemanager.com.outerspacemanager.viewmodel.MainViewModel;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private MainViewModel vm;

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
        this.showMainFragment(savedInstanceState);
        this.vm.replaceDetailFragment(new BuildingsFragment());
        Timber.d("Layout inflated");

        ButterKnife.bind(this);
        Timber.d("Views binded");

        // Now listen...
        Timber.d("Listening to data mutations and UI events...");
    }

    private void configureDagger(){ AndroidInjection.inject(this); }

    private void configureViewModel(){
        this.vm = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
        this.vm.init();

        // Observe data mutations...
        this.vm.getCurrentDetailFragment().observe(
                this,
                this::showDetailFragment
        );
    }

    private void showMainFragment(Bundle savedInstanceState){
        if (savedInstanceState != null) return;

        MainFragment mainFragment = new MainFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_container, mainFragment, null)
                .commit();
    }

    private void showDetailFragment(Fragment fragment) {
        Timber.d("Switching detail fragment to %s", fragment.toString());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.detail_container, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.vm.refreshUserStats();
        return true;
    }
}
