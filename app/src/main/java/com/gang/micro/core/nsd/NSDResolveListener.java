package com.gang.micro.core.nsd;

import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

import com.gang.micro.core.microscope.Microscope;

import java.net.InetAddress;

public class NSDResolveListener implements NsdManager.ResolveListener {

    private final NSDAsyncDiscoveryTask owningAsyncTask;

    public NSDResolveListener(NSDAsyncDiscoveryTask owningAsyncTask) {
        this.owningAsyncTask = owningAsyncTask;
    }

    @Override
    public void onResolveFailed(NsdServiceInfo nsdServiceInfo, int i) {

        Log.d(this.getClass().getName(), "Resolve failed. Error code: " + i);
    }

    @Override
    public void onServiceResolved(NsdServiceInfo nsdServiceInfo) {

        // Get host
        InetAddress host = nsdServiceInfo.getHost();

        // Get address
        String address = host.getHostAddress();

        // Create microscope instance
        Microscope microscope = new Microscope(nsdServiceInfo.getServiceName(), address);

        Log.d(this.getClass().getName(), "Resolved address = " + address);

        // Notify progress to owning async task
        owningAsyncTask.publish(microscope);
    }
}
