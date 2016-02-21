package com.gang.micro.core.microscope;

public class Microscope {

    private String name;
    private String serverIp;
    private String streamingPort;
    private String webApplicationPort;

    public Microscope(String name, String serverIp) {
        this.name = name;
        this.serverIp = serverIp;
    }

    public String getName() {
        return name;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getStreamingPort() {
        return streamingPort;
    }

    public void setStreamingPort(String streamingPort) {
        this.streamingPort = streamingPort;
    }

    public String getWebApplicationPort() {
        return webApplicationPort;
    }

    public void setWebApplicationPort(String webApplicationPort) {
        this.webApplicationPort = webApplicationPort;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Microscope))
            return false;
        if (o == this)
            return true;

        Microscope object = (Microscope) o;

        return serverIp.equals(((Microscope) o).getServerIp()) && name.equals(object.getName());
    }
}
