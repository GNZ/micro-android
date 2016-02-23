package com.gang.micro.gallery.common;

public interface GalleryService {

    void loadImages();

    void deleteImage(int position);

    void updateImage(int position);

    void finishedLoadingItems();
}
