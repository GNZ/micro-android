package com.gang.micro.core.utils.image;

import android.content.Context;

import com.gang.micro.core.MicroApplication;
import com.gang.micro.core.image.Image;

public class ImageUtils {

    private static final String SERVER_IMAGE_PATH = "images/output/";
    private static final String IMAGE_EXTENSION = ".jpg";
    public static final int WIDTH = 800;
    public static final int HEIGTH = 800;

    private final MicroApplication application;

    public ImageUtils(Context context) {

        this.application = (MicroApplication) context.getApplicationContext();
    }

    public String getImageUrl(Image image) {
        String serverIp = application.getServerIp();

        return "http://" + serverIp + "/" + SERVER_IMAGE_PATH + image.getId().toString() + IMAGE_EXTENSION;
    }
}
