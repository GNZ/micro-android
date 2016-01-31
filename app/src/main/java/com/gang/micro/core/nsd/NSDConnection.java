package com.gang.micro.core.nsd;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdManager.DiscoveryListener;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

import java.net.InetAddress;
import java.util.HashMap;


public class NSDConnection {

    private static final String TAG = "NSDConnection";
    private static final String SERVICE_TYPE = "_workstation._tcp."; //TODO
    private static String serviceName = "ubuntu";
    //private static final String serviceName = "ubuntu";
    private Context context;
    private NsdManager nsdManager;
    private NsdManager.ResolveListener resolveListener;
    private DiscoveryListener discoveryListener;
    private HashMap<String,Microscope> microscopes;


    private void initializeDiscoveryListener() {
        discoveryListener = new DiscoveryListener() {

            @Override
            public void onStartDiscoveryFailed(String serviceType, int errorCode) {

            }

            @Override
            public void onStopDiscoveryFailed(String serviceType, int errorCode) {
                nsdManager.stopServiceDiscovery(this);
            }

            @Override
            public void onDiscoveryStarted(String serviceType) {
            }

            @Override
            public void onDiscoveryStopped(String serviceType) {
                //nsdManager.stopServiceDiscovery(this);
                nsdManager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener);
            }

            @Override
            public void onServiceFound(NsdServiceInfo serviceInfo) {
                String name = serviceInfo.getServiceName();
                String type = serviceInfo.getServiceType();
                Log.d(TAG, "Service name: " + name);
                Log.d(TAG, "Service type: " + type);
                if (type.equals(SERVICE_TYPE) && name.contains(serviceName)) {
                    Log.d(TAG, "Service found @'" + name + "'");
                    nsdManager.resolveService(serviceInfo, resolveListener);
                }
            }

            @Override
            public void onServiceLost(NsdServiceInfo serviceInfo) {

            }
        };
    }

    private void initializeResolveListener() {
        resolveListener = new NsdManager.ResolveListener() {

            @Override
            public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // Called when the resolve fails.  Use the error code to debug.
                Log.e(TAG, "Resolve failed" + errorCode);
            }

            @Override
            public void onServiceResolved(NsdServiceInfo serviceInfo) {
                // Port is being returned as 9. Not needed.
                //int port = mServiceInfo.getPort();

                InetAddress host = serviceInfo.getHost();
                String address = host.getHostAddress();
                Log.d(TAG, "Resolved address = " + address);
                microscopes.put(address, new Microscope(serviceInfo.getServiceName(), address));
            }
        };
    }

    public NSDConnection(Context context, String serviceName) {
        microscopes = new HashMap<>();
        //this.serviceName = serviceName;
        nsdManager = (NsdManager) context.getSystemService(Context.NSD_SERVICE);
        initializeResolveListener();
        initializeDiscoveryListener();
    }

    public void discover(){
        nsdManager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener);
        Log.d(TAG, "discovery");
        //nsdManager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener);
    }

    public void startDiscovery() {

    }

    public void stopDiscover(){
        microscopes = new HashMap<>();
        //discoveryListener.onDiscoveryStopped(SERVICE_TYPE);
        nsdManager.stopServiceDiscovery(discoveryListener);
        //nsdManager = null;
        //discoveryListener = null;
        //resolveListener = null;
    }

    public HashMap<String,Microscope> getMicroscopes(){
        return microscopes;
    }

    public void setServiceName(String serviceName){ this.serviceName = serviceName; }

}
