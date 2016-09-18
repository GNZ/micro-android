package com.gang.micro.microscope;

import android.util.Log;

import com.gang.micro.application.Preferences;

public class MicroscopeProvider {

    private Microscope mMicroscope;

    private final Preferences mPreferences;

    public MicroscopeProvider(Preferences preferences) {
        this.mPreferences = preferences;
    }

    public Microscope getmMicroscope() {
        return mMicroscope;
    }

    public void setmMicroscope(Microscope mMicroscope) {
        this.mMicroscope = mMicroscope;
    }

    public String getStreamingPort() {
        if (mMicroscope != null && mMicroscope.getStreamingPort() != null) {
            return mMicroscope.getStreamingPort();
        }

        return mPreferences.getStreamingPort();
    }

    public String getWebApplicationPort() {
        if (mMicroscope != null && mMicroscope.getWebApplicationPort() != null) {
            return mMicroscope.getWebApplicationPort();
        }

        return mPreferences.getWebApplicationPort();
    }

    public String getServerIp() {
        if (mMicroscope == null) {
            Log.e(getClass().getName(), "Requested server IP without associated microscope.");
        }

        return mMicroscope.getServerIp();
    }
}
