package lamit.outerspacemanager.com.outerspacemanager.ui.fragment;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnTextChanged;
import dagger.android.support.AndroidSupportInjection;
import lamit.outerspacemanager.com.outerspacemanager.R;
import lamit.outerspacemanager.com.outerspacemanager.model.Ship;
import lamit.outerspacemanager.com.outerspacemanager.ui.adapter.ShipsListItemAdapter;
import lamit.outerspacemanager.com.outerspacemanager.viewmodel.ShipyardViewModel;
import timber.log.Timber;


public class ShipyardFragment extends Fragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ShipyardViewModel vm;

    @BindView(R.id.ships_listview)                      ListView shipsListView;
    @BindView(R.id.ships_amount_edittext)               EditText amountEditText;
    @BindView(R.id.ships_amount_zero_button)            Button amountZeroButton;
    @BindView(R.id.ships_amount_minus_hundred_button)   Button amountMinusHundredButton;
    @BindView(R.id.ships_amount_minus_ten_button)       Button amountMinusTenButton;
    @BindView(R.id.ships_amount_minus_one_button)       Button amountMinusOneButton;
    @BindView(R.id.ships_amount_plus_one_button)        Button amountPlusOneButton;
    @BindView(R.id.ships_amount_plus_ten_button)        Button amountPlusTenButton;
    @BindView(R.id.ships_amount_plus_hundred_button)    Button amountPlusHundredButton;
    @BindView(R.id.ships_amount_max_togglebutton)       Button amountMaxToggleButton;

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
        this.shipsListView.setAdapter(this.shipsListItemAdapter);
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
                user -> {
                    if (user != null) {
                        updateUI(user.getGas(), user.getMinerals());
                        refresh();
                    }}
        );

        this.vm.getShips().observe(
                this,
                ships -> { if (ships != null && ships.size() > 0) updateUI(ships); }
        );

        this.vm.getAmount().observe(
                this,
                this::updateUI
        );

        this.vm.getMaxMode().observe(
                this,
                this::updateUI
        );
    }

    private void updateUI(List<Ship> ships){

        this.shipsListItemAdapter.setObjects(ships);
        Timber.d("Updated listview objects");
    }

    private void updateUI(double userGas, double userMinerals){

        this.shipsListItemAdapter.setUserCurrentGas(userGas);
        this.shipsListItemAdapter.setUserCurrentMinerals(userMinerals);
        Timber.d("Updated listview user resources");
    }

    private void updateUI(int amount){

        this.shipsListItemAdapter.setCurrentAmount(amount);
        Timber.d("Updated listview amounts");
    }

    private void updateUI(boolean maxMode){

        this.shipsListItemAdapter.setMaxMode(maxMode);
        Timber.d("Updated listview max mode");
    }

    private void refresh(){

        this.vm.refreshShips();
        Timber.d("Refreshing binded data...");
    }

    @OnItemClick(R.id.ships_listview)
    public void onItemClick(View clickedView, int position){

        int buildAmount = (int) clickedView.getTag(R.id.ship_item_build_amount_tag);

        this.vm.createShip(position, buildAmount);
    }

    @OnTextChanged(value = R.id.ships_amount_edittext, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onAfterAmountChanged(Editable text){

        if (!text.toString().equals(""))
            this.vm.setAmount(Integer.valueOf(text.toString()));
    }

    @OnClick({
            R.id.ships_amount_zero_button,
            R.id.ships_amount_minus_hundred_button,
            R.id.ships_amount_minus_ten_button,
            R.id.ships_amount_minus_one_button,
            R.id.ships_amount_plus_one_button,
            R.id.ships_amount_plus_ten_button,
            R.id.ships_amount_plus_hundred_button
    })
    public void onAmountButtonClick(Button button){

        int amount = Integer.valueOf(button.getTag(R.id.ships_amount_amount_tag).toString());
        String method = button.getTag(R.id.ships_amount_method_tag).toString();

        Integer previousValue = Integer.valueOf(this.amountEditText.getText().toString());
        int newValue = 0;

        switch(method){
            case "set":
                newValue = amount;
                break;
            case "plus":
                newValue = previousValue + amount;
                break;
            case "minus":
                newValue = previousValue - amount;
                break;
            default:
        }

        newValue = newValue < 0 ? 0 : newValue;

        this.amountEditText.setText(String.valueOf(newValue));
    }

    @OnCheckedChanged(R.id.ships_amount_max_togglebutton)
    public void onMaxModeChange(boolean isMaxMode){

        this.vm.setMaxMode(isMaxMode);

        this.amountZeroButton.setEnabled(!isMaxMode);
        this.amountMinusHundredButton.setEnabled(!isMaxMode);
        this.amountMinusTenButton.setEnabled(!isMaxMode);
        this.amountMinusOneButton.setEnabled(!isMaxMode);
        this.amountEditText.setEnabled(!isMaxMode);
        this.amountPlusOneButton.setEnabled(!isMaxMode);
        this.amountPlusTenButton.setEnabled(!isMaxMode);
        this.amountPlusHundredButton.setEnabled(!isMaxMode);
    }

}
