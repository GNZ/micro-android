package com.gang.micro.core;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.gang.micro.core.microscope.Microscope;


public class MicroApplication extends Application {

    private final String defaultStreamingPort;
    private final String defaultWebApplicationPort;
    private String defaultServiceName;
    private String defaultProtocol;
    private String defaultStreamingPath;

    private Microscope currentMicroscope;

    private boolean changes;

    public MicroApplication() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        defaultServiceName = sharedPreferences.getString("service_name", "micro");
        defaultProtocol = sharedPreferences.getString("protocol", "http");
        defaultStreamingPath = sharedPreferences.getString("folder", "action=stream");
        defaultWebApplicationPort = sharedPreferences.getString("webapp_port", "5000");
        defaultStreamingPort = sharedPreferences.getString("streaming_port", "8080");
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
}
