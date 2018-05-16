package lamit.outerspacemanager.com.outerspacemanager.di.builder;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import lamit.outerspacemanager.com.outerspacemanager.di.annotation.PerFragment;
import lamit.outerspacemanager.com.outerspacemanager.ui.fragment.BuildingsFragment;
import lamit.outerspacemanager.com.outerspacemanager.ui.fragment.GalaxyFragment;
import lamit.outerspacemanager.com.outerspacemanager.ui.fragment.MainFragment;
import lamit.outerspacemanager.com.outerspacemanager.ui.fragment.SearchesFragment;
import lamit.outerspacemanager.com.outerspacemanager.ui.fragment.ShipyardFragment;

@Module
public abstract class FragmentBuilder {

    @PerFragment
    @ContributesAndroidInjector
    abstract MainFragment contributeMainFragment();

    @PerFragment
    @ContributesAndroidInjector
    abstract BuildingsFragment contributeBuildingsFragment();

    @PerFragment
    @ContributesAndroidInjector
    abstract SearchesFragment contributeSearchesFragment();

    @PerFragment
    @ContributesAndroidInjector
    abstract ShipyardFragment contributeShipyardFragment();

    @PerFragment
    @ContributesAndroidInjector
    abstract GalaxyFragment contributeGalaxyFragment();
}
