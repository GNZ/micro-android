package com.gang.micro;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.gang.micro.gallery.local.LocalGalleryAdapter;
import com.gang.micro.microscope.Microscope;


public class MicroApplication extends Application {

    private Microscope currentMicroscope;

    //LocalGalleryAdapter
    LocalGalleryAdapter localGalleryAdapter;

    SharedPreferences sharedPreferences;

    private boolean changes;

    public MicroApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //LeakCanary.install(this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
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

    public void setLocalGalleryAdapter(LocalGalleryAdapter localGalleryAdapter) {
        this.localGalleryAdapter = localGalleryAdapter;
    }

    public String getStreamingPort() {
        if (currentMicroscope != null && currentMicroscope.getStreamingPort() != null) {
            return currentMicroscope.getStreamingPort();
        }

        return sharedPreferences.getString("streaming_port", "8080");
    }

    public String getWebApplicationPort() {
        if (currentMicroscope != null && currentMicroscope.getWebApplicationPort() != null) {
            return currentMicroscope.getWebApplicationPort();
        }

        return sharedPreferences.getString("webapp_port", "5000");
    }

    public String getFolderName() {
        return sharedPreferences.getString("folder", "action=stream");
    }

    public String getServiceName() {
        return sharedPreferences.getString("service_name", "micro");
    }

    public String getServerIp() {
        if (currentMicroscope == null) {
            Log.e(getClass().getName(), "Requested server IP without associated microscope.");
        }

        return currentMicroscope.getServerIp();
    }

    public String getProtocol() {
        return sharedPreferences.getString("protocol", "http");
    }

    public LocalGalleryAdapter getLocalGalleryAdapter() {
        return localGalleryAdapter;
    }
}
