package com.gang.micro.core.microscope;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.gang.micro.ui.PreviewActivity;

public class MicroscopeListItemClickListener implements AdapterView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        // Get context
        Context context = adapterView.getContext();

        // Load clicked microscope
        Microscope microscope = (Microscope) adapterView.getItemAtPosition(i);

        // Create PreviewActivity intent
        Intent intent = new Intent(context, PreviewActivity.class);

        // Put microscope_ip as extra
        intent.putExtra(PreviewActivity.EXTRA_MICROSCOPE_IP, microscope.getIp());

        // Start activity
        context.startActivity(intent);
    }
}
