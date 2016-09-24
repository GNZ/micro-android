package com.gang.micro.microscope;

import android.content.Context;

import com.gang.micro.dagger.ForActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class MicroscopesFragmentModule {

    private final MicroscopesFragment microscopeFragment;

    public MicroscopesFragmentModule (MicroscopesFragment microscopesFragment) {
        this.microscopeFragment = microscopesFragment;
    }

    @Provides
    MicroscopesFragment providesMicroscopesFragment() {
        return microscopeFragment;
    }
}
