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
import android.widget.Button;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;
import lamit.outerspacemanager.com.outerspacemanager.R;
import lamit.outerspacemanager.com.outerspacemanager.model.User;
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
        Timber.d("Listening to data mutations and UI events...");
    }

    private void configureDagger(){
        AndroidSupportInjection.inject(this);
    }

    private void configureViewModel(){
        //String userLogin = getArguments().getString(UID_KEY);
        this.vm = ViewModelProviders.of(this, viewModelFactory).get(BuildingsViewModel.class);
        this.vm.init();

        this.vm.getUser().observe(
                this,
                this::updateUI
        );
    }

    private void updateUI(User user){
        //
    }
}
