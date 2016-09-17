package com.gang.micro.application;

import android.content.Context;

import com.gang.micro.api.MicroApiSpecification;
import com.gang.micro.dagger.ForApplication;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {

    void inject(MicroApplication microApplication);

    MicroApplication getMicroApplication();

    @ForApplication
    Context getContext();

    Preferences getPrefereces();

    MicroApiSpecification getMicroApi();

}
