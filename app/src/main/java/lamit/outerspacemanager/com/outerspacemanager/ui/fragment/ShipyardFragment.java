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
import android.widget.ListView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import dagger.android.support.AndroidSupportInjection;
import lamit.outerspacemanager.com.outerspacemanager.R;
import lamit.outerspacemanager.com.outerspacemanager.model.Building;
import lamit.outerspacemanager.com.outerspacemanager.model.Ship;
import lamit.outerspacemanager.com.outerspacemanager.model.User;
import lamit.outerspacemanager.com.outerspacemanager.ui.adapter.BuildingsListItemAdapter;
import lamit.outerspacemanager.com.outerspacemanager.ui.adapter.ShipsListItemAdapter;
import lamit.outerspacemanager.com.outerspacemanager.viewmodel.BuildingsViewModel;
import lamit.outerspacemanager.com.outerspacemanager.viewmodel.GalaxyViewModel;
import lamit.outerspacemanager.com.outerspacemanager.viewmodel.SearchesViewModel;
import lamit.outerspacemanager.com.outerspacemanager.viewmodel.ShipyardViewModel;
import timber.log.Timber;

public class ShipyardFragment extends Fragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ShipyardViewModel vm;

    @BindView(R.id.ships_listview) ListView shipsListView;

    ShipsListItemAdapter shipsListItemAdapter;

    public ShipyardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout and bind views
        View view = inflater.inflate(R.layout.fragment_shipyard, container, false);
        Timber.d("Layout Inflated");

        ButterKnife.bind(this, view);
        this.shipsListItemAdapter = new ShipsListItemAdapter(getContext());
        shipsListView.setAdapter(this.shipsListItemAdapter);
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

        // Now listen...
        Timber.d("Listening to data mutations...");
    }

    private void configureDagger(){
        AndroidSupportInjection.inject(this);
    }

    private void configureViewModel(){
        this.vm = ViewModelProviders.of(this, viewModelFactory).get(ShipyardViewModel.class);
        this.vm.init();

        this.vm.getUser().observe(
                this,
                user -> { if (user != null) refresh();}
        );

        this.vm.getShips().observe(
                this,
                ships -> { if (ships != null && ships.size() > 0) updateUI(ships); }
        );
    }

    private void updateUI(List<Ship> ships){
        this.shipsListItemAdapter.setObjects(ships);
        Timber.d("Updated listview objects");
    }

    private void refresh(){
        //refreshUser ?
        this.vm.refreshShips();
        Timber.d("Refreshing binded data...");
    }

    @OnItemClick(R.id.ships_listview)
    public void onItemClick(int position){
        this.vm.createShip(position);
    }

}
