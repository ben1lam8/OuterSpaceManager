package lamit.outerspacemanager.com.outerspacemanager.di;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import lamit.outerspacemanager.com.outerspacemanager.ui.fragment.BuildingsFragment;
import lamit.outerspacemanager.com.outerspacemanager.ui.fragment.GalaxyFragment;
import lamit.outerspacemanager.com.outerspacemanager.ui.fragment.MainFragment;
import lamit.outerspacemanager.com.outerspacemanager.ui.fragment.SearchesFragment;
import lamit.outerspacemanager.com.outerspacemanager.ui.fragment.ShipyardFragment;

@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract MainFragment contributeMainFragment();

    @ContributesAndroidInjector
    abstract BuildingsFragment contributeBuildingsFragment();

    @ContributesAndroidInjector
    abstract SearchesFragment contributeSearchesFragment();

    @ContributesAndroidInjector
    abstract ShipyardFragment contributeShipyardFragment();

    @ContributesAndroidInjector
    abstract GalaxyFragment contributeGalaxyFragment();
}
