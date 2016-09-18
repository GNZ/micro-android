package com.gang.micro.dagger;

import android.content.Context;
import android.content.res.Resources;

import com.gang.micro.application.ApplicationComponent;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = ApplicationComponent.class,
        modules = {
                ActivityModule.class,
        }
)
public interface BaseActivityComponent {

    void inject(BaseActivity baseActivity);

    @ForActivity
    Resources getResources();

    @ForActivity
    Context getActivityContext();
}
