package lamit.outerspacemanager.com.outerspacemanager.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import lamit.outerspacemanager.com.outerspacemanager.model.Credentials;
import lamit.outerspacemanager.com.outerspacemanager.model.Token;
import lamit.outerspacemanager.com.outerspacemanager.model.User;
import lamit.outerspacemanager.com.outerspacemanager.repository.UserRepository;

public class SignupViewModel extends ViewModel {

    private UserRepository userRepo;

    @Inject
    public SignupViewModel(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public void init(){
        // Nothing to init here
    }

    public void createUser(Credentials credentials){
        userRepo.createUser(credentials);
    }
}
