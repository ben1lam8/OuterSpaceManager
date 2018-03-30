package lamit.outerspacemanager.com.outerspacemanager.ui.fragment;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import dagger.android.support.AndroidSupportInjection;
import lamit.outerspacemanager.com.outerspacemanager.R;
import lamit.outerspacemanager.com.outerspacemanager.model.Building;
import lamit.outerspacemanager.com.outerspacemanager.model.User;
import lamit.outerspacemanager.com.outerspacemanager.ui.adapter.BuildingsListItemAdapter;
import lamit.outerspacemanager.com.outerspacemanager.viewmodel.BuildingsViewModel;
import lamit.outerspacemanager.com.outerspacemanager.viewmodel.MainViewModel;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuildingsFragment extends Fragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private BuildingsViewModel vm;

    @BindView(R.id.buildings_listview) ListView buildingsListView;

    BuildingsListItemAdapter buildingsListItemAdapter;

    public BuildingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout and bind views
        View view = inflater.inflate(R.layout.fragment_buildings, container, false);
        Timber.d("Layout Inflated");

        ButterKnife.bind(this, view);
        this.buildingsListItemAdapter = new BuildingsListItemAdapter(getContext());
        buildingsListView.setAdapter(this.buildingsListItemAdapter);
        Timber.d("Views binded");

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Inject dependencies
        this.configureDagger();
        Timber.d("Dependencies injected");

        // Configure VM
        this.configureViewModel();
        Timber.d("ViewModel ready");

        // Now observe...
        Timber.d("Observing data mutations...");
    }

    private void configureDagger(){
        AndroidSupportInjection.inject(this);
    }

    private void configureViewModel(){
        this.vm = ViewModelProviders.of(this, viewModelFactory).get(BuildingsViewModel.class);
        this.vm.init();

        this.vm.getUser().observe(
                this,
                user -> { if (user != null) refresh();}
        );

        this.vm.getBuildings().observe(
                this,
                buildings -> { if (buildings != null && buildings.size() > 0) updateUI(buildings); }
        );
    }

    private void updateUI(List<Building> buildings){
        this.buildingsListItemAdapter.setObjects(buildings);
        Timber.d("Updated listview objects");
    }

    private void refresh(){
        //refreshUser ?
        this.vm.refreshBuildings();
        Timber.d("Refreshing binded data...");
    }

    @OnItemClick(R.id.buildings_listview)
    public void onItemClick(int position){
        this.vm.createBuilding(position);
    }
}
