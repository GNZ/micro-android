package com.gang.micro.nsd;

import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

public class NSDDiscoveryListener implements NsdManager.DiscoveryListener {

    private final String nsdServiceType;
    private final String nsdServiceName;

    private NSDCoordinator ownerCoordinator;

    public NSDDiscoveryListener(String nsdServiceType, String nsdServiceName, NSDCoordinator ownerCoordinator) {
        this.nsdServiceType = nsdServiceType;
        this.nsdServiceName = nsdServiceName;
        this.ownerCoordinator = ownerCoordinator;
    }

    @Override
    public void onStartDiscoveryFailed(String s, int i) {

    }

    @Override
    public void onStopDiscoveryFailed(String s, int i) {

    }

    @Override
    public void onDiscoveryStarted(String s) {

    }

    @Override
    public void onDiscoveryStopped(String s) {

    }

    @Override
    public void onServiceFound(NsdServiceInfo serviceInfo) {
        String name = serviceInfo.getServiceName();
        String type = serviceInfo.getServiceType();

        // Log service discovery success
        Log.d(this.getClass().getName(), "Service name: " + name + ". Service type: " + type);

        // If the service is the desired one...
        if (type.equals(nsdServiceType) && name.contains(nsdServiceName)) {

            // Log found service name
            Log.d(this.getClass().getName(), "Known service found @'" + name + "'");

            // Notify coordinator to resolve service
            ownerCoordinator.resolveService(serviceInfo);
        } else {

            // Log unknown service name
            Log.d(this.getClass().getName(), "Unknown service found @'" + name + "'");
        }
    }

    @Override
    public void onServiceLost(NsdServiceInfo nsdServiceInfo) {

    }
}
