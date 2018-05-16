package lamit.outerspacemanager.com.outerspacemanager.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import lamit.outerspacemanager.com.outerspacemanager.model.Report;
import lamit.outerspacemanager.com.outerspacemanager.model.User;
import lamit.outerspacemanager.com.outerspacemanager.repository.ReportRepository;
import lamit.outerspacemanager.com.outerspacemanager.repository.UserRepository;


public class GalaxyViewModel extends ViewModel {

    private UserRepository userRepo;
    private LiveData<User> user;

    private ReportRepository reportRepo;
    private LiveData<List<Report>> reports;

    @Inject
    public GalaxyViewModel(UserRepository userRepo, ReportRepository reportRepo) {
        this.userRepo = userRepo;
        this.reportRepo = reportRepo;
    }

    public void init() {
        if (this.user == null) {
            this.user = this.userRepo.getLastConnectedFreshUser();
        }

        if (this.reports == null) {
            this.reports = this.reportRepo.getReports();
        }
    }

    public void refreshReports(){
        if(this.user.getValue() != null) {
            this.reportRepo.refreshReports(this.user.getValue().getToken());
        }
    }

    public LiveData<User> getUser() { return this.user; }

    public LiveData<List<Report>> getReports() { return this.reports; }
}