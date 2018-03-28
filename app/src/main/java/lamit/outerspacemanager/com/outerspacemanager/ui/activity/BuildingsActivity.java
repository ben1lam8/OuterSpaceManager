package lamit.outerspacemanager.com.outerspacemanager.ui.activity;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import lamit.outerspacemanager.com.outerspacemanager.R;
import lamit.outerspacemanager.com.outerspacemanager.ui.fragment.BuildingsFragment;
import lamit.outerspacemanager.com.outerspacemanager.viewmodel.BuildingsViewModel;
import timber.log.Timber;

public class BuildingsActivity extends AppCompatActivity implements HasSupportFragmentInjector, AdapterView.OnItemClickListener{

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private BuildingsViewModel vm;

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
        this.vm = ViewModelProviders.of(this, viewModelFactory).get(BuildingsViewModel.class);
        this.vm.init();

        // Observe data mutations...
    }

    private void showFragment(Bundle savedInstanceState){
        if (savedInstanceState != null) return;

        BuildingsFragment buildingsFragment = new BuildingsFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.buildings_container, buildingsFragment, null)
                .commit();
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        /*Building building = this.buildings.get(i);
        Log.i(TAG, "Click sur «Construction» pour le bâtiment suivant : "+ building.toString());

        //this.createBuilding(building.getBuildingId(), this.token.getToken());*/

    }
}
