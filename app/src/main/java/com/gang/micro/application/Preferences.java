package com.gang.micro.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.gang.micro.dagger.ForApplication;

public class Preferences {

    private final SharedPreferences sharedPreferences;

    public Preferences(@ForApplication Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getStreamingPort() {
        return sharedPreferences.getString("streaming_port", "8080");
    }

    public String getWebApplicationPort() {
        return sharedPreferences.getString("webapp_port", "5000");
    }

    public String getFolderName() {
        return sharedPreferences.getString("folder", "action=stream");
    }

    public String getServiceName() {
        return sharedPreferences.getString("service_name", "micro");
    }

    public String getProtocol() {
        return sharedPreferences.getString("protocol", "http");
    }


}
