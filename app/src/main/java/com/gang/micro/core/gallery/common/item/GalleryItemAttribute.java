package com.gang.micro.core.gallery.common.item;

public class GalleryItemAttribute {

    private String name;
    private String value;

    public GalleryItemAttribute(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
