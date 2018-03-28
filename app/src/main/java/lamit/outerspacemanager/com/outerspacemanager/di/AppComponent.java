package lamit.outerspacemanager.com.outerspacemanager.di;


import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import lamit.outerspacemanager.com.outerspacemanager.OuterSpaceManager;

@Singleton
@Component(modules={ActivityModule.class, FragmentModule.class, AppModule.class})
public interface AppComponent extends AndroidInjector<OuterSpaceManager> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }

    void inject(OuterSpaceManager outerSpaceManager);
}
