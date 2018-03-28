package lamit.outerspacemanager.com.outerspacemanager.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import java.util.List;
import javax.inject.Inject;
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

        if (this.ships == null && user.getValue() != null) {
            this.ships = this.shipRepo.getShips(user.getValue().getToken());
        }
    }

    public LiveData<User> getUser(){
        return this.user;
    }

}
