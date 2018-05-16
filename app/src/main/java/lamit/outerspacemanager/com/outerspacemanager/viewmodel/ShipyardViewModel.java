package lamit.outerspacemanager.com.outerspacemanager.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import java.util.List;
import javax.inject.Inject;

import lamit.outerspacemanager.com.outerspacemanager.model.Ship;
import lamit.outerspacemanager.com.outerspacemanager.model.Ship;
import lamit.outerspacemanager.com.outerspacemanager.model.User;
import lamit.outerspacemanager.com.outerspacemanager.repository.ShipRepository;
import lamit.outerspacemanager.com.outerspacemanager.repository.UserRepository;


public class ShipyardViewModel extends ViewModel {

    private UserRepository userRepo;
    private LiveData<User> user;

    private ShipRepository shipRepo;
    private LiveData<List<Ship>> ships;

    private MutableLiveData<Integer> amount;

    private MutableLiveData<Boolean> maxMode;

    @Inject
    public ShipyardViewModel(UserRepository userRepo, ShipRepository shipRepo) {
        this.userRepo = userRepo;
        this.shipRepo = shipRepo;
    }

    public void init() {
        if (this.user == null) {
            this.user = this.userRepo.getLastConnectedFreshUser();
        }

        if (this.ships == null) {
            this.ships = this.shipRepo.getShips();
        }

        if (this.amount == null) {
            this.amount = new MutableLiveData<>();
            this.amount.setValue(1);
        }

        if (this.maxMode == null) {
            this.maxMode = new MutableLiveData<>();
        }
    }

    public LiveData<User> getUser(){
        return this.user;
    }

    public LiveData<List<Ship>> getShips() {
        this.refreshShips();
        return this.ships;
    }

    public void refreshShips(){
        if(this.user.getValue() != null) {
            this.shipRepo.refreshShips(this.user.getValue().getToken());
            this.shipRepo.refreshFleet(this.user.getValue().getToken());
        }
    }

    public void createShip(int index, int buildAmount){

        if (this.ships.getValue() == null || this.getUser().getValue() == null) return;

        Ship ship = this.ships.getValue().get(index);
        this.shipRepo.createShip(ship, buildAmount, this.getUser().getValue().getToken());
    }

    public MutableLiveData<Integer> getAmount() {
        return amount;
    }

    public MutableLiveData<Integer> setAmount(int amount) {
        this.amount.setValue(amount);
        return this.amount;
    }

    public MutableLiveData<Boolean> getMaxMode() {
        return maxMode;
    }

    public MutableLiveData<Boolean> setMaxMode(boolean maxMode) {
        this.maxMode.setValue(maxMode);
        return this.maxMode;
    }
}
