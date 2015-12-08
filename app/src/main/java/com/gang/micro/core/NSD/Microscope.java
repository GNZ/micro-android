package com.gang.micro.core.NSD;

/**
 * Created by Gonza on 08/12/2015.
 */
public class Microscope {

    private String name;
    private String ip;

    public Microscope(String name, String ip) {
        this.name = name;
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }
}
