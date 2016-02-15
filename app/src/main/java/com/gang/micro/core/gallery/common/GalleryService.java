package com.gang.micro.core.gallery.common;

public interface GalleryService {

    void loadImages();

    void deleteImage(int position);

    void updateImage(int position);

    void finishedLoadingItems();
}
