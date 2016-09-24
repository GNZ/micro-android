package com.gang.micro.application;

import android.content.Context;

import com.gang.micro.dagger.ForApplication;
import com.gang.micro.microscope.MicroscopeProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private static MicroApplication sMicroApplication;

    public ApplicationModule(MicroApplication microApplication) {
        sMicroApplication = microApplication;
    }

    @Provides
    MicroApplication provideMicroApplication() {
        return sMicroApplication;
    }

    @Provides
    @Singleton
    @ForApplication
    Context provideContext() {
        return sMicroApplication.getApplicationContext();
    }

    @Provides
    @Singleton
    Preferences providePreferences(@ForApplication Context context) {
        return new Preferences(context);
    }

    @Provides
    @Singleton
    MicroscopeProvider provideMircoscope(Preferences preferences) {
        return new MicroscopeProvider(preferences);
    }
}
