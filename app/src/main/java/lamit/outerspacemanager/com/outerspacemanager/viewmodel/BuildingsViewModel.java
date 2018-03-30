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

        if (this.buildings == null) {
            this.buildings = this.buildingRepo.getBuildings();
        }
    }

    public LiveData<User> getUser() { return this.user; }

    public LiveData<List<Building>> getBuildings() {
        this.refreshBuildings();
        return this.buildings;
    }

    public void refreshBuildings(){
        if(this.user.getValue() != null) {
            this.buildingRepo.refreshBuildings(this.user.getValue().getToken());
        }
    }

    public void createBuilding(int index){

        if (this.buildings.getValue() == null || this.getUser().getValue() == null) return;

        Building building = this.buildings.getValue().get(index);
        this.buildingRepo.createBuilding(building, this.getUser().getValue().getToken());
    }
}