package lamit.outerspacemanager.com.outerspacemanager.di.component;


import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import lamit.outerspacemanager.com.outerspacemanager.OuterSpaceManager;
import lamit.outerspacemanager.com.outerspacemanager.di.builder.ActivityBuilder;
import lamit.outerspacemanager.com.outerspacemanager.di.module.AppModule;
import lamit.outerspacemanager.com.outerspacemanager.di.builder.FragmentBuilder;
import lamit.outerspacemanager.com.outerspacemanager.di.module.GlideApp;
import lamit.outerspacemanager.com.outerspacemanager.di.module.GlideAppModule;

@Singleton
@Component(modules={AndroidInjectionModule.class, AppModule.class, ActivityBuilder.class, FragmentBuilder.class})
public interface AppComponent {

    void inject(OuterSpaceManager outerSpaceManager);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
