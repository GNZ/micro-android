package com.gang.micro.start;

import com.gang.micro.dagger.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class StartActivityModule {

    private final StartActivity startActivity;

    public StartActivityModule(StartActivity startActivity) {
        this.startActivity = startActivity;
    }

    @Provides
    @ActivityScope
    StartActivity providesStartActivity() {
        return startActivity;
    }
}
