package lamit.outerspacemanager.com.outerspacemanager.di.builder;


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
public abstract class ActivityBuilder {

    @ContributesAndroidInjector()
    abstract LoginActivity contributeLoginActivity();

    @ContributesAndroidInjector()
    abstract SignupActivity contributeSignupActivity();

    @ContributesAndroidInjector(modules = FragmentBuilder.class)
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector(modules = FragmentBuilder.class)
    abstract BuildingsActivity contributeBuildingsActivity();

    @ContributesAndroidInjector(modules = FragmentBuilder.class)
    abstract SearchesActivity contributeSearchesActivity();

    @ContributesAndroidInjector(modules = FragmentBuilder.class)
    abstract ShipyardActivity contributeShipyardActivity();

    @ContributesAndroidInjector(modules = FragmentBuilder.class)
    abstract GalaxyActivity contributeGalaxyActivity();
}
