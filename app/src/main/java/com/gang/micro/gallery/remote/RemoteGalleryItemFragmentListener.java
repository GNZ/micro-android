package com.gang.micro.gallery.remote;

import com.gang.micro.image.Image;

public interface RemoteGalleryItemFragmentListener {

    void saveImageInLocalGallery(Image image, String url);
}
