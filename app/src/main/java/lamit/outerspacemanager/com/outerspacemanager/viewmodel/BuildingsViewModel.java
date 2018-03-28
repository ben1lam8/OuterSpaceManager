package lamit.outerspacemanager.com.outerspacemanager.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import lamit.outerspacemanager.com.outerspacemanager.model.Building;
import lamit.outerspacemanager.com.outerspacemanager.model.User;
import lamit.outerspacemanager.com.outerspacemanager.repository.BuildingRepository;
import lamit.outerspacemanager.com.outerspacemanager.repository.UserRepository;


public class BuildingsViewModel extends ViewModel {

    private UserRepository userRepo;
    private LiveData<User> user;

    private BuildingRepository buildingRepo;
    private LiveData<List<Building>> buildings;

    @Inject
    public BuildingsViewModel(UserRepository userRepo, BuildingRepository buildingRepo) {
        this.userRepo = userRepo;
        this.buildingRepo = buildingRepo;
    }

    public void init() {
        if (this.user == null) {
            this.user = this.userRepo.getLastConnectedFreshUser();
        }

        if (this.buildings == null && user.getValue() != null) {
            this.buildings = this.buildingRepo.getBuildings(user.getValue().getToken());
        }
    }

    public LiveData<User> getUser() { return this.user; }

    public LiveData<List<Building>> getBuildings() { return this.buildings; }

    public void refreshBuildings(String token){
        this.buildingRepo.refreshBuildings(token);
    }
}