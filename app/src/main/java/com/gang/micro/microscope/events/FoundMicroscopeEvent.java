package com.gang.micro.microscope.events;


import com.gang.micro.microscope.Microscope;

public class FoundMicroscopeEvent {

    private Microscope microscope;

    public FoundMicroscopeEvent(Microscope microscope) {

        this.microscope = microscope;

    }

    public Microscope getMicroscope() {
        return microscope;
    }
}
