package com.gang.micro.gallery.remote;

import com.gang.micro.application.ApplicationComponent;
import com.gang.micro.dagger.ActivityModule;
import com.gang.micro.dagger.ActivityScope;
import com.gang.micro.dagger.BaseActivityComponent;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = ApplicationComponent.class,
        modules = ActivityModule.class
)
public interface RemoteGalleryActivityComponent extends BaseActivityComponent {

    void inject(RemoteGalleryActivity remoteGalleryActivity);

}
