package lamit.outerspacemanager.com.outerspacemanager.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import lamit.outerspacemanager.com.outerspacemanager.model.User;
import lamit.outerspacemanager.com.outerspacemanager.repository.UserRepository;


public class GalaxyViewModel extends ViewModel {

    private UserRepository userRepo;
    private LiveData<User> user;

    @Inject
    public GalaxyViewModel(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public void init() {
        if (this.user != null) return;
        this.user = userRepo.getLastConnectedFreshUser();
    }

    public LiveData<User> getUser() { return this.user; }
}