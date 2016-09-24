package com.gang.micro.dagger;

import android.support.v4.app.Fragment;

import com.gang.micro.application.ApplicationComponent;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = ApplicationComponent.class,
        modules = {
                FragmentModule.class
        }
)
public interface BaseFragmentComponent {
    void inject(Fragment fragment);
}