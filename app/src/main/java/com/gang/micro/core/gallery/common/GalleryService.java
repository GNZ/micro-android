package com.gang.micro.core.gallery.common;

import android.graphics.Bitmap;

import com.gang.micro.core.image.Image;

public interface GalleryService {

    void loadImages();

    void deleteImage(int position);

    void updateImage(int position);

    void saveImage(Bitmap bitmap, Image image);

    void finishedLoadingItems();
}
