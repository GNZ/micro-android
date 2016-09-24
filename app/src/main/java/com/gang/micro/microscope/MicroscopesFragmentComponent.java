package com.gang.micro.microscope;

import android.content.Context;

import com.gang.micro.start.StartActivityComponent;
import com.gang.micro.dagger.BaseFragmentComponent;
import com.gang.micro.dagger.ForActivity;
import com.gang.micro.dagger.FragmentModule;
import com.gang.micro.dagger.FragmentScope;

import dagger.Component;

@FragmentScope
@Component(
        dependencies = StartActivityComponent.class,
        modules = {
                FragmentModule.class,
                MicroscopesFragmentModule.class
        }
)
public interface MicroscopesFragmentComponent extends BaseFragmentComponent {

    void inject(MicroscopesFragment microscopesFragment);

    MicroscopesFragment getMicroscopeFragment();

    @ForActivity
    Context getContext();

    MicroscopeProvider getMicroscopeProvider();

}
