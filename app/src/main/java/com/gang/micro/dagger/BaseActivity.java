package com.gang.micro.dagger;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    private BaseActivityComponent activityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activityComponent = createActivityComponent();
        super.onCreate(savedInstanceState);
    }

    @NonNull
    protected abstract BaseActivityComponent createActivityComponent();

    @NonNull
    public BaseActivityComponent getActivityComponent() {
        return activityComponent;
    }

}
