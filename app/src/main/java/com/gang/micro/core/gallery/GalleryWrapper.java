package com.gang.micro.core.gallery;


import com.gang.micro.core.image.Image;

public interface GalleryWrapper {

    void deleteImage();

    void showImage(Image image);

    void closeImage();
}

