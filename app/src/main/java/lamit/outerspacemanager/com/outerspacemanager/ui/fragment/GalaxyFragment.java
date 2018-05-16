package lamit.outerspacemanager.com.outerspacemanager.ui.fragment;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import lamit.outerspacemanager.com.outerspacemanager.R;
import lamit.outerspacemanager.com.outerspacemanager.model.Report;
import lamit.outerspacemanager.com.outerspacemanager.model.User;
import lamit.outerspacemanager.com.outerspacemanager.ui.adapter.ReportsListItemAdapter;
import lamit.outerspacemanager.com.outerspacemanager.viewmodel.GalaxyViewModel;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalaxyFragment extends Fragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private GalaxyViewModel vm;

    @BindView(R.id.reports_recycler_view)  RecyclerView reportsRecyclerView;
    ReportsListItemAdapter reportsListItemAdapter;

    public GalaxyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout and bind views
        View view = inflater.inflate(R.layout.fragment_galaxy, container, false);
        Timber.d("Layout Inflated");

        ButterKnife.bind(this, view);
        this.reportsListItemAdapter = new ReportsListItemAdapter();
        this.reportsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.reportsRecyclerView.setAdapter(this.reportsListItemAdapter);
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
        this.vm = ViewModelProviders.of(this, viewModelFactory).get(GalaxyViewModel.class);
        this.vm.init();

        this.vm.getUser().observe(
                this,
                user -> {
                    if (user != null) {
                        refresh();
                    }}
        );

        this.vm.getReports().observe(
                this,
                this::updateUI
        );
    }

    private void updateUI(User user){
        //
    }

    private void updateUI(List<Report> reports){
        this.reportsListItemAdapter.setObjects(reports);
        Timber.d("Updated listview objects");
    }

    private void refresh(){

        this.vm.refreshReports();
        Timber.d("Refreshing binded data...");
    }
}
