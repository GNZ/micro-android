package com.gang.micro.core.microscope;

import android.content.Context;
import android.content.Intent;
import android.view.View;

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

        // Create MicroStreamActivity intent
        Intent intent = new Intent(context, MicroStreamActivity.class);

        // Put microscope_ip as extra
        intent.putExtra(MicroStreamActivity.EXTRA_MICROSCOPE_IP, microscope.getIp());

        // Start activity
        context.startActivity(intent);
    }
}
