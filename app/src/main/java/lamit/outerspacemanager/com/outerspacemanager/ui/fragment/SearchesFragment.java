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
import lamit.outerspacemanager.com.outerspacemanager.model.Search;
import lamit.outerspacemanager.com.outerspacemanager.model.User;
import lamit.outerspacemanager.com.outerspacemanager.ui.adapter.BuildingsListItemAdapter;
import lamit.outerspacemanager.com.outerspacemanager.ui.adapter.SearchesListItemAdapter;
import lamit.outerspacemanager.com.outerspacemanager.viewmodel.BuildingsViewModel;
import lamit.outerspacemanager.com.outerspacemanager.viewmodel.GalaxyViewModel;
import lamit.outerspacemanager.com.outerspacemanager.viewmodel.SearchesViewModel;
import timber.log.Timber;

public class SearchesFragment extends Fragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private SearchesViewModel vm;

    @BindView(R.id.searches_listview) ListView searchesListView;

    SearchesListItemAdapter searchesListItemAdapter;

    public SearchesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout and bind views
        View view = inflater.inflate(R.layout.fragment_searches, container, false);
        Timber.d("Layout Inflated");

        ButterKnife.bind(this, view);
        this.searchesListItemAdapter = new SearchesListItemAdapter(getContext());
        searchesListView.setAdapter(this.searchesListItemAdapter);
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
        this.vm = ViewModelProviders.of(this, viewModelFactory).get(SearchesViewModel.class);
        this.vm.init();

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
        //refreshUser ?
        this.vm.refreshSearches();
        Timber.d("Refreshing binded data...");
    }

    @OnItemClick(R.id.searches_listview)
    public void onItemClick(int position){ this.vm.upgradeSearch(position); }

}
