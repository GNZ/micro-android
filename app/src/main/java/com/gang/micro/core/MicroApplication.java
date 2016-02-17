package com.gang.micro.core;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.gang.micro.core.gallery.local.LocalGalleryAdapter;
import com.gang.micro.core.microscope.Microscope;
import com.squareup.leakcanary.LeakCanary;


public class MicroApplication extends Application {

    private String defaultStreamingPort;
    private String defaultWebApplicationPort;
    private String defaultServiceName;
    private String defaultProtocol;
    private String defaultStreamingPath;

    private Microscope currentMicroscope;

    //LocalGalleryAdapter
    LocalGalleryAdapter localGalleryAdapter;

    private boolean changes;

    public MicroApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        defaultServiceName = sharedPreferences.getString("service_name", "micro");
        defaultProtocol = sharedPreferences.getString("protocol", "http");
        defaultStreamingPath = sharedPreferences.getString("folder", "action=stream");
        defaultWebApplicationPort = sharedPreferences.getString("webapp_port", "5000");
        defaultStreamingPort = sharedPreferences.getString("streaming_port", "8080");
    }

    public Microscope getCurrentMicroscope() {
        return currentMicroscope;
    }

    public void setCurrentMicroscope(Microscope currentMicroscope) {
        this.currentMicroscope = currentMicroscope;
    }

    public boolean getChanges() {
        return changes;
    }

    public void setChanges(boolean changes) {
        this.changes = changes;
    }

    public void setLocalGalleryAdapter(LocalGalleryAdapter localGalleryAdapter){
        this.localGalleryAdapter = localGalleryAdapter;
    }

    public String getStreamingPort() {
        if (currentMicroscope != null && currentMicroscope.getStreamingPort() != null) {
            return currentMicroscope.getStreamingPort();
        }

        return defaultStreamingPort;
    }

    public String getWebApplicationPort() {
        if (currentMicroscope != null && currentMicroscope.getWebApplicationPort() != null) {
            return currentMicroscope.getWebApplicationPort();
        }

        return defaultWebApplicationPort;
    }

    public String getFolderName() {
        return defaultStreamingPath;
    }

    public String getServiceName() {
        return defaultServiceName;
    }

    public String getServerIp() {
        if (currentMicroscope == null) {
            Log.e(getClass().getName(), "Requested server IP without associated microscope.");
        }

        return currentMicroscope.getServerIp();
    }

    public String getProtocol() {
        return defaultProtocol;
    }

    public LocalGalleryAdapter getLocalGalleryAdapter() {
        return localGalleryAdapter;
    }
}
