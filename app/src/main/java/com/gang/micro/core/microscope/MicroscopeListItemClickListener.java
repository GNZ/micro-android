package com.gang.micro.core.microscope;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.gang.micro.ui.PreviewActivity;

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

        // Create PreviewActivity intent
        Intent intent = new Intent(context, PreviewActivity.class);

        // Put microscope_ip as extra
        intent.putExtra(PreviewActivity.EXTRA_MICROSCOPE_IP, microscope.getIp());

        // Start activity
        context.startActivity(intent);
    }
}
