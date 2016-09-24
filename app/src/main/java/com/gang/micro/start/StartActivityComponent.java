package com.gang.micro.start;

import com.gang.micro.dagger.ActivityModule;
import com.gang.micro.dagger.ActivityScope;
import com.gang.micro.dagger.BaseActivityComponent;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = { ActivityModule.class, StartActivityModule.class}
)
public interface StartActivityComponent extends BaseActivityComponent {

    StartActivity inject(StartActivity startActivity);

}
