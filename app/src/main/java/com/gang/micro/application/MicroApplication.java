package com.gang.micro.application;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.gang.micro.gallery.local.LocalGalleryAdapter;
import com.gang.micro.microscope.Microscope;

import javax.inject.Inject;


public class MicroApplication extends Application {

    private Microscope currentMicroscope;

    private ApplicationComponent mApplicationComponent;

    //LocalGalleryAdapter
    LocalGalleryAdapter localGalleryAdapter;

    @Inject
    Preferences mPreferences;

    private boolean changes;

    public MicroApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //LeakCanary.install(this);
        setupDaggerGraph();
    }

    private void setupDaggerGraph() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        mApplicationComponent.inject(this);
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

        return mPreferences.getStreamingPort();
    }

    public String getWebApplicationPort() {
        if (currentMicroscope != null && currentMicroscope.getWebApplicationPort() != null) {
            return currentMicroscope.getWebApplicationPort();
        }

        return mPreferences.getWebApplicationPort();
    }

    public String getFolderName() {
        return mPreferences.getFolderName();
    }

    public String getServiceName() {
        return mPreferences.getServiceName();
    }

    public String getServerIp() {
        if (currentMicroscope == null) {
            Log.e(getClass().getName(), "Requested server IP without associated microscope.");
        }

        return currentMicroscope.getServerIp();
    }

    public String getProtocol() {
        return mPreferences.getProtocol();
    }

    public LocalGalleryAdapter getLocalGalleryAdapter() {
        return localGalleryAdapter;
    }
}
