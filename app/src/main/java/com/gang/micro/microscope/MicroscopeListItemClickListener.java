package com.gang.micro.microscope;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.gang.micro.application.MicroApplication;
import com.gang.micro.MicroStreamActivity;
import com.gang.micro.dagger.ForActivity;
import com.gang.micro.nsd.events.StopNSDDiscoveryEvent;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

public class MicroscopeListItemClickListener {

    private final Context context;
    private final MicroscopeListAdapter adapter;
    private final MicroscopeProvider microscopeProvider;

    @Inject
    public MicroscopeListItemClickListener(@ForActivity Context context, MicroscopeListAdapter adapter,
                                           @NonNull MicroscopeProvider microscopeProvider) {

        this.context = context;
        this.adapter = adapter;
        this.microscopeProvider = microscopeProvider;
    }

    public void onItemClick(int position, View view) {

        // Get microscope from adapter
        Microscope microscope = adapter.getItemAtPosition(position);

        // Set microscope as current
        MicroApplication app = ((MicroApplication) adapter.getContext().getApplicationContext());
        microscopeProvider.setMicroscope(microscope);

        // Stop microscope discovery
        EventBus.getDefault().post(new StopNSDDiscoveryEvent());

        // Create MicroStreamActivity intent
        Intent intent = new Intent(context, MicroStreamActivity.class);

        // Start activity
        context.startActivity(intent);
    }
}
