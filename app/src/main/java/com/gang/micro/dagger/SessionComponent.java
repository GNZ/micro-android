package com.gang.micro.dagger;

import com.gang.micro.api.NetworkModule;

import dagger.Subcomponent;

@SessionScope
@Subcomponent(
        modules = {
                NetworkModule.class,
                SessionModule.class
        }
)
public interface SessionComponent {

//        RemoteGalleryActivityComponent plus(RemoteGalleryActivityModule module);

//        MicroStreamActivityComponent plus(MicroStreamActivityModule module);
}
