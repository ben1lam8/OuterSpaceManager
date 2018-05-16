package lamit.outerspacemanager.com.outerspacemanager.di.builder;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import lamit.outerspacemanager.com.outerspacemanager.di.annotation.PerActivity;
import lamit.outerspacemanager.com.outerspacemanager.ui.activity.BuildingsActivity;
import lamit.outerspacemanager.com.outerspacemanager.ui.activity.GalaxyActivity;
import lamit.outerspacemanager.com.outerspacemanager.ui.activity.LoginActivity;
import lamit.outerspacemanager.com.outerspacemanager.ui.activity.MainActivity;
import lamit.outerspacemanager.com.outerspacemanager.ui.activity.SearchesActivity;
import lamit.outerspacemanager.com.outerspacemanager.ui.activity.ShipyardActivity;
import lamit.outerspacemanager.com.outerspacemanager.ui.activity.SignupActivity;

@Module
public abstract class ActivityBuilder {

    @PerActivity
    @ContributesAndroidInjector()
    abstract LoginActivity contributeLoginActivity();

    @PerActivity
    @ContributesAndroidInjector()
    abstract SignupActivity contributeSignupActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = FragmentBuilder.class)
    abstract MainActivity contributeMainActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = FragmentBuilder.class)
    abstract BuildingsActivity contributeBuildingsActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = FragmentBuilder.class)
    abstract SearchesActivity contributeSearchesActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = FragmentBuilder.class)
    abstract ShipyardActivity contributeShipyardActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = FragmentBuilder.class)
    abstract GalaxyActivity contributeGalaxyActivity();
}
