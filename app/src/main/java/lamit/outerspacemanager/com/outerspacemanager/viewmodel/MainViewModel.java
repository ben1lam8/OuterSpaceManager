package lamit.outerspacemanager.com.outerspacemanager.viewmodel;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.app.Fragment;

import javax.inject.Inject;

import lamit.outerspacemanager.com.outerspacemanager.model.User;
import lamit.outerspacemanager.com.outerspacemanager.repository.UserRepository;
import timber.log.Timber;

public class MainViewModel extends ViewModel{

    private UserRepository userRepo;
    private LiveData<User> user;

    private MutableLiveData<Fragment> currentDetailFragment;

    @Inject
    public MainViewModel(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public void init() {

        if (this.user == null){
            this.user = userRepo.getLastConnectedFreshUser();
        }

        if (this.currentDetailFragment == null){
            this.currentDetailFragment = new MutableLiveData<>();
        }
    }

    public LiveData<User> getUser() {
        this.refreshUserStats();
        return this.user;
    }

    public void refreshUserStats(){
        if(this.user.getValue() == null) return;

        Timber.d("Refreshing user stats...");
        userRepo.fetchUser(this.user.getValue());
    }

    public void disconnect(){
        if(this.user.getValue() == null) return;
        userRepo.disconnectUser(this.user.getValue());

        Timber.d("%s disconnected", this.user.getValue().getUsername());
    }

    public MutableLiveData<Fragment> getCurrentDetailFragment(){
        return this.currentDetailFragment;
    }

    public void replaceDetailFragment(Fragment fragment){
        this.currentDetailFragment.setValue(fragment);
    }
}
