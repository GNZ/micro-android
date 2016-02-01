package com.gang.micro.core.nsd;

import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;

public class NSDCoordinator {

    private static final String SERVICE_TYPE = "_workstation._tcp."; //TODO
    private static final String SERVICE_NAME = "micro";

    private NsdManager nsdManager;
    private NsdManager.ResolveListener resolveListener;
    private NsdManager.DiscoveryListener discoveryListener;

    public NSDCoordinator(NsdManager nsdManager, NSDAsyncDiscoveryTask nsdAsyncDiscoveryTask) {
        this.resolveListener = new NSDResolveListener(nsdAsyncDiscoveryTask);

        this.discoveryListener = new NSDDiscoveryListener(SERVICE_TYPE, SERVICE_NAME, this);

        this.nsdManager = nsdManager;
    }

    public void startDiscovery() throws InterruptedException {
        Thread.currentThread().sleep(100);

        nsdManager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener);
    }

    public void stopDiscovery() {
        nsdManager.stopServiceDiscovery(discoveryListener);
    }

    public void resolveService(NsdServiceInfo serviceInfo) {
        nsdManager.resolveService(serviceInfo, resolveListener);
    }
}
