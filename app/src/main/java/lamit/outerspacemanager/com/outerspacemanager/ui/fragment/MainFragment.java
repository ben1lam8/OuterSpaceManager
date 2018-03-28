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
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;
import lamit.outerspacemanager.com.outerspacemanager.R;
import lamit.outerspacemanager.com.outerspacemanager.model.User;
import lamit.outerspacemanager.com.outerspacemanager.viewmodel.MainViewModel;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private MainViewModel vm;

    @BindView(R.id.main_username_textview)  TextView usernameTextView;
    @BindView(R.id.main_score_textview)     TextView scoreTextView;
    @BindView(R.id.main_buildings_button)   Button buildingsButton;
    @BindView(R.id.main_lab_button)         Button labButton;
    @BindView(R.id.main_shipyard_button)    Button shipyardButton;
    @BindView(R.id.main_galaxy_button)      Button galaxyButton;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout and bind views
        View view = inflater.inflate(R.layout.fragment_main, container, false);
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
        // Share same VM as the containing activity
        vm = ViewModelProviders.of(getActivity(), viewModelFactory).get(MainViewModel.class);
        vm.init();

        vm.getUser().observe(
                this,
                this::updateUI
        );
    }

    private void updateUI(User user){
        Timber.d("Update UI");
        this.usernameTextView.setText(getString(R.string.main_username_textview, user.getUsername()));
        this.scoreTextView.setText(getString(R.string.main_score_textview, user.getPoints()));
    }

    @OnClick(R.id.main_buildings_button)
    public void showBuildingsDetail(Button button){
        Timber.d("Click on %s", button.getText());
        this.vm.replaceDetailFragment(new BuildingsFragment());
    }

    @OnClick(R.id.main_lab_button)
    public void showSearchesDetail(Button button){
        Timber.d("Click on %s", button.getText());
        this.vm.replaceDetailFragment(new SearchesFragment());
    }

    @OnClick(R.id.main_shipyard_button)
    public void showShipyardDetail(Button button){
        Timber.d("Click on %s", button.getText());
        this.vm.replaceDetailFragment(new ShipyardFragment());
    }

    @OnClick(R.id.main_galaxy_button)
    public void showGalaxyDetail(Button button){
        Timber.d("Click on %s", button.getText());
        this.vm.replaceDetailFragment(new GalaxyFragment());
    }
}
