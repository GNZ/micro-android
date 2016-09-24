package com.gang.micro.dagger;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    protected abstract void injectComponent(@NonNull BaseActivityComponent baseActivityComponent,
                                            @NonNull FragmentModule fragmentModule,
                                            @Nullable Bundle savedInstanceState);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final BaseActivityComponent activityComponent = ((BaseActivity) getActivity())
                .getActivityComponent();
        injectComponent(activityComponent, new FragmentModule(this), savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }
}
