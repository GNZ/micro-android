package com.gang.micro.core.nsd;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.os.AsyncTask;

import com.gang.micro.core.microscope.Microscope;
import com.gang.micro.core.microscope.MicroscopeListAdapter;

public class NSDAsyncDiscoveryTask extends AsyncTask<Void, Microscope, Void> {

    private static final int TIMEOUT_MILLIS = 2000;
    private final MicroscopeListAdapter microscopeListAdapter;
    private final NSDCoordinator nsdCoordinator;

    public NSDAsyncDiscoveryTask(MicroscopeListAdapter microscopeListAdapter) {
        this.microscopeListAdapter = microscopeListAdapter;

        // Get NsdManager reference
        NsdManager nsdManager = (NsdManager) microscopeListAdapter.getContext().getSystemService(Context.NSD_SERVICE);

        // Build NSD Coordinator with NSDManager and reference to this AsyncTask
        this.nsdCoordinator = new NSDCoordinator(nsdManager, this);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            // Start discovery
            nsdCoordinator.startDiscovery();

            // Wait TIMEOUT_MILLIS
            Thread.currentThread().sleep(TIMEOUT_MILLIS);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

            // Always stop discovery
            nsdCoordinator.stopDiscovery();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Microscope... values) {
        microscopeListAdapter.addAll(values[0]);
    }

    public void publish(Microscope microscope) {
        publishProgress(microscope);
    }
}
