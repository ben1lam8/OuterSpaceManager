package lamit.outerspacemanager.com.outerspacemanager;


import android.app.Activity;
import android.app.Application;
import javax.inject.Inject;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

// Compiled import (if this import fails, try to rebuild)
import lamit.outerspacemanager.com.outerspacemanager.di.component.DaggerAppComponent;

public class OuterSpaceManager extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initDagger();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    private void initDagger(){
        DaggerAppComponent.builder().application(this).build().inject(this);
    }
}