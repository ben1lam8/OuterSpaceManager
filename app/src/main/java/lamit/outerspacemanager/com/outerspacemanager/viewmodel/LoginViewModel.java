package lamit.outerspacemanager.com.outerspacemanager.viewmodel;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import javax.inject.Inject;
import lamit.outerspacemanager.com.outerspacemanager.model.Credentials;
import lamit.outerspacemanager.com.outerspacemanager.model.User;
import lamit.outerspacemanager.com.outerspacemanager.repository.UserRepository;
import timber.log.Timber;

public class LoginViewModel extends ViewModel {

    private UserRepository userRepo;
    private LiveData<User> connectedUser;

    @Inject
    public LoginViewModel(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public void init(){
        if (this.connectedUser != null) return;
        this.connectedUser = userRepo.getLastConnectedFreshUser();
    }

    public LiveData<User> getConnectedUser() { return this.connectedUser; }

    public void connectUser(Credentials credentials){

        this.connectedUser = userRepo.getByUsername(credentials.getUsername());

        Timber.d("Current credentials : %s", credentials);
        this.userRepo.fetchUserToken(this.connectedUser.getValue(), credentials);
    }

}
