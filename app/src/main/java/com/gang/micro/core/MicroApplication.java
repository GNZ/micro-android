package com.gang.micro.core;

import android.app.Application;


public class MicroApplication extends Application {

    private String serverIP;

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public String getServerIP() {
        return serverIP;
    }
}
