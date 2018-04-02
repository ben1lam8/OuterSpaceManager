package lamit.outerspacemanager.com.outerspacemanager.viewmodel;

import android.arch.lifecycle.LiveData;
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

    public void createShip(int index){

        if (this.ships.getValue() == null || this.getUser().getValue() == null) return;

        Ship ship = this.ships.getValue().get(index);
        this.shipRepo.createShip(ship, 1, this.getUser().getValue().getToken());
    }

}
