package com.gang.micro.core;

import android.app.Application;


public class MicroApplication extends Application {

    private String serverIP;
    private String serviceName;
    private String protocol;
    private String port;
    private String folderName;
    private boolean changes;

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setChanges(boolean changes){
        this.changes = changes;
    }

    public String getServerIP() {
        return serverIP;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getPort() {
        return port;
    }

    public String getFolderName() {
        return folderName;
    }

    public boolean getChanges() {
        return changes;
    }
}
