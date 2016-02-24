package com.gang.micro.nsd;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.os.AsyncTask;
import android.util.Log;

import com.gang.micro.microscope.Microscope;
import com.gang.micro.microscope.MicroscopeListAdapter;

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
    protected void onCancelled() {
        super.onCancelled();
        nsdCoordinator.stopDiscovery();
        microscopeListAdapter.getMicroscopeFragment().updateUI();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            // Start discovery
            nsdCoordinator.startDiscovery();

            // Wait TIMEOUT_MILLIS
            Thread.currentThread().sleep(TIMEOUT_MILLIS);

        } catch (InterruptedException e) {

            // Log discovery was interrupted
            Log.d("NsdDiscovery", "Discovery interrupted");

        } finally {
            if (!isCancelled()) {
                // Always stop discovery if it wasn't cancel
                nsdCoordinator.stopDiscovery();
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Microscope... values) {
        microscopeListAdapter.addAll(values[0]);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        microscopeListAdapter.getMicroscopeFragment().updateUI();
    }

    public void publish(Microscope microscope) {
        publishProgress(microscope);
    }
}
