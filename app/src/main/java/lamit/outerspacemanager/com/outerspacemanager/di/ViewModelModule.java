package lamit.outerspacemanager.com.outerspacemanager.di;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import lamit.outerspacemanager.com.outerspacemanager.viewmodel.BuildingsViewModel;
import lamit.outerspacemanager.com.outerspacemanager.viewmodel.GalaxyViewModel;
import lamit.outerspacemanager.com.outerspacemanager.viewmodel.LoginViewModel;
import lamit.outerspacemanager.com.outerspacemanager.viewmodel.MainViewModel;
import lamit.outerspacemanager.com.outerspacemanager.viewmodel.SearchesViewModel;
import lamit.outerspacemanager.com.outerspacemanager.viewmodel.ShipyardViewModel;
import lamit.outerspacemanager.com.outerspacemanager.viewmodel.SignupViewModel;
import lamit.outerspacemanager.com.outerspacemanager.viewmodel.ViewModelFactory;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel loginViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SignupViewModel.class)
    abstract ViewModel bindSignupViewModel(SignupViewModel signupViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel mainViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(BuildingsViewModel.class)
    abstract ViewModel bindBuildingsViewModel(BuildingsViewModel buildingsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ShipyardViewModel.class)
    abstract ViewModel bindShipyardViewModel(ShipyardViewModel shipyardViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SearchesViewModel.class)
    abstract ViewModel bindSearchesViewModel(SearchesViewModel searchesViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(GalaxyViewModel.class)
    abstract ViewModel bindGalaxyViewModel(GalaxyViewModel galaxyViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
