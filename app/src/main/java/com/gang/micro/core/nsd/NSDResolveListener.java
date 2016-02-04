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

        // Get service name (replace to avoid "\032" string)
        String serviceName = getServiceName(nsdServiceInfo);

        // Get host
        InetAddress host = nsdServiceInfo.getHost();

        // Get address
        String address = host.getHostAddress();

        // Create microscope instance
        Microscope microscope = new Microscope(serviceName, address);

        Log.d(this.getClass().getName(), "Resolved address = " + address);

        // Notify progress to owning async task
        owningAsyncTask.publish(microscope);
    }

    private String getServiceName(NsdServiceInfo nsdServiceInfo) {
        String serviceName = nsdServiceInfo.getServiceName();

        int bugIndex = serviceName.indexOf("\\032");

        return serviceName.substring(0, bugIndex);

    }
}
