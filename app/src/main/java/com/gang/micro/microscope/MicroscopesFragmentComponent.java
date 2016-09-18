package com.gang.micro.microscope;

import com.gang.micro.StartActivityComponent;
import com.gang.micro.dagger.BaseFragmentComponent;
import com.gang.micro.dagger.FragmentModule;
import com.gang.micro.dagger.FragmentScope;

import dagger.Component;

@FragmentScope
@Component(
        dependencies = StartActivityComponent.class,
        modules = {
                FragmentModule.class
        }
)
public interface MicroscopesFragmentComponent extends BaseFragmentComponent {

    void inject(MicroscopesFragment microscopesFragment);

}
