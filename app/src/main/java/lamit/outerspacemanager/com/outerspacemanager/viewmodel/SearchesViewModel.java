package lamit.outerspacemanager.com.outerspacemanager.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import lamit.outerspacemanager.com.outerspacemanager.model.Search;
import lamit.outerspacemanager.com.outerspacemanager.model.User;
import lamit.outerspacemanager.com.outerspacemanager.repository.SearchRepository;
import lamit.outerspacemanager.com.outerspacemanager.repository.UserRepository;


public class SearchesViewModel extends ViewModel {

    private UserRepository userRepo;
    private LiveData<User> user;

    private SearchRepository searchRepo;
    private LiveData<List<Search>> searches;

    @Inject
    public SearchesViewModel(UserRepository userRepo, SearchRepository searchRepo) {
        this.userRepo = userRepo;
        this.searchRepo = searchRepo;
    }

    public void init() {
        if (this.user == null) {
            this.user = this.userRepo.getLastConnectedFreshUser();
        }

        if (this.searches == null) {
            this.searches = this.searchRepo.getSearches();
        }
    }

    public LiveData<User> getUser(){
        return this.user;
    }

    public LiveData<List<Search>> getSearches() {
        this.refreshSearches();
        return this.searches;
    }

    public void refreshSearches(){
        if(this.user.getValue() != null) {
            this.searchRepo.refreshSearches(this.user.getValue().getToken());
        }
    }

    public void upgradeSearch(int index){

        if (this.searches.getValue() == null || this.getUser().getValue() == null) return;

        Search search = this.searches.getValue().get(index);
        this.searchRepo.upgradeSearch(search, this.getUser().getValue().getToken());
    }
}