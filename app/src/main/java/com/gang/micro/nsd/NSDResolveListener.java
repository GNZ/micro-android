package com.gang.micro.nsd;

import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

import com.gang.micro.microscope.Microscope;
import com.gang.micro.microscope.events.FoundMicroscopeEvent;

import org.greenrobot.eventbus.EventBus;

import java.net.InetAddress;

public class NSDResolveListener implements NsdManager.ResolveListener {

    public NSDResolveListener() {

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
        EventBus.getDefault().post(new FoundMicroscopeEvent(microscope));
    }

    private String getServiceName(NsdServiceInfo nsdServiceInfo) {
        String serviceName = nsdServiceInfo.getServiceName();

        String toReturn;

        // Remove \32
        int bugIndex = serviceName.indexOf("\\032");
        toReturn = bugIndex >= 0 ? serviceName.substring(0, bugIndex) : serviceName;

        // Remove mac
        int macPosition = toReturn.indexOf(" [");
        toReturn = macPosition >= 0 ? serviceName.substring(0, macPosition) : toReturn;

        return toReturn;
    }
}
