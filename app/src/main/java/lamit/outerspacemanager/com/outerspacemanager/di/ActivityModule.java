package lamit.outerspacemanager.com.outerspacemanager.di;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import lamit.outerspacemanager.com.outerspacemanager.ui.activity.BuildingsActivity;
import lamit.outerspacemanager.com.outerspacemanager.ui.activity.GalaxyActivity;
import lamit.outerspacemanager.com.outerspacemanager.ui.activity.LoginActivity;
import lamit.outerspacemanager.com.outerspacemanager.ui.activity.MainActivity;
import lamit.outerspacemanager.com.outerspacemanager.ui.activity.SearchesActivity;
import lamit.outerspacemanager.com.outerspacemanager.ui.activity.ShipyardActivity;
import lamit.outerspacemanager.com.outerspacemanager.ui.activity.SignupActivity;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector()
    abstract LoginActivity contributeLoginActivity();

    @ContributesAndroidInjector()
    abstract SignupActivity contributeSignupActivity();

    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract BuildingsActivity contributeBuildingsActivity();

    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract SearchesActivity contributeSearchesActivity();

    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract ShipyardActivity contributeShipyardActivity();

    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract GalaxyActivity contributeGalaxyActivity();
}
