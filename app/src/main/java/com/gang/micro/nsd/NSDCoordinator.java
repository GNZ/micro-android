package com.gang.micro.nsd;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;

import com.gang.micro.nsd.events.StartNSDDiscoveryEvent;
import com.gang.micro.nsd.events.StopNSDDiscoveryEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class NSDCoordinator {

    private static final String SERVICE_TYPE = "_workstation._tcp."; //TODO
    private static final String SERVICE_NAME = "micro";
    private static final int TIME_OUT = 3000;

    private NsdManager nsdManager;
    private NsdManager.ResolveListener resolveListener;
    private NsdManager.DiscoveryListener discoveryListener;

    // checks if the NsdManager is discovering services
    private boolean discovering = false;

    public NSDCoordinator(NsdManager nsdManager) {

        EventBus.getDefault().register(this);

        this.resolveListener = new NSDResolveListener();

        this.discoveryListener = new NSDDiscoveryListener(SERVICE_TYPE, SERVICE_NAME, this);

        this.nsdManager = nsdManager;
    }

    @Override
    protected void finalize() throws Throwable {

        EventBus.getDefault().unregister(this);

        super.finalize();
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void startDiscovery(StartNSDDiscoveryEvent startNSDDiscoveryEvent) {

        nsdManager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener);

        discovering = true;

        try {
            Thread.currentThread().sleep(TIME_OUT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            EventBus.getDefault().post(new StopNSDDiscoveryEvent());
        }

    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void stopDiscovery(StopNSDDiscoveryEvent stopNSDDiscoveryEvent) {

        if (discovering) {

            nsdManager.stopServiceDiscovery(discoveryListener);

            discovering = false;
        }

    }

    public void startDiscovery() throws InterruptedException {

        nsdManager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener);
    }

    public void stopDiscovery() {
        nsdManager.stopServiceDiscovery(discoveryListener);
    }

    public void resolveService(NsdServiceInfo serviceInfo) {
        nsdManager.resolveService(serviceInfo, resolveListener);
    }
}
