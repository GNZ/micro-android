package com.gang.micro.core.microscope;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.gang.micro.core.MicroApplication;
import com.gang.micro.ui.MicroStreamActivity;

public class MicroscopeListItemClickListener {

    private final Context context;
    private final MicroscopeListAdapter adapter;

    public MicroscopeListItemClickListener(Context context, MicroscopeListAdapter adapter) {

        this.context = context;
        this.adapter = adapter;
    }

    public void onItemClick(int position, View view) {

        // Get microscope from adapter
        Microscope microscope = adapter.getItemAtPosition(position);

        // Set microscope as current
        MicroApplication app = ((MicroApplication) adapter.getContext().getApplicationContext());
        app.setCurrentMicroscope(microscope);

        // Create MicroStreamActivity intent
        Intent intent = new Intent(context, MicroStreamActivity.class);

        // Start activity
        context.startActivity(intent);
    }
}
