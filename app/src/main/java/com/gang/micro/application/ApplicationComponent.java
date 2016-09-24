package com.gang.micro.application;

import android.content.Context;

import com.gang.micro.dagger.ActivityModule;
import com.gang.micro.start.StartActivityComponent;
import com.gang.micro.start.StartActivityModule;
import com.gang.micro.api.MicroApiSpecification;
import com.gang.micro.api.NetworkModule;
import com.gang.micro.dagger.ForApplication;
import com.gang.micro.dagger.SessionComponent;
import com.gang.micro.dagger.SessionModule;
import com.gang.micro.microscope.MicroscopeProvider;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(MicroApplication microApplication);

    MicroApplication getMicroApplication();

    @ForApplication
    Context getContext();

    SessionComponent plus(SessionModule sessionModule, NetworkModule networkModule);

    StartActivityComponent plus(StartActivityModule startActivityModule, ActivityModule activityModule);

    Preferences getPrefereces();

    MicroscopeProvider getMicroscopeProvider();

}
