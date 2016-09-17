package com.gang.micro.utils.image;

import android.content.Context;

import com.gang.micro.application.MicroApplication;
import com.gang.micro.image.Image;

public class ImageUtils {

    private static final String SERVER_IMAGE_PATH = "images/output/";
    private static final String SERVER_THUMBNAIL_PATH = "images/output/thumb/";
    private static final String IMAGE_EXTENSION = ".jpg";
    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;

    private final MicroApplication application;

    public ImageUtils(Context context) {

        this.application = (MicroApplication) context.getApplicationContext();
    }

    public String getImageUrl(Image image) {
        String serverIp = application.getServerIp();

        return "http://" + serverIp + "/" + SERVER_IMAGE_PATH + image.getId().toString() + IMAGE_EXTENSION;
    }

    public String getImageThumbnailUrl(Image image) {
        String serverIp = application.getServerIp();

        return "http://" + serverIp + "/" + SERVER_THUMBNAIL_PATH + image.getId().toString() + IMAGE_EXTENSION;
    }

}
