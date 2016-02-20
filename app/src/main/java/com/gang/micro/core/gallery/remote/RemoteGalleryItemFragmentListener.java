package com.gang.micro.core.gallery.remote;

import com.gang.micro.core.image.Image;

public interface RemoteGalleryItemFragmentListener {

    void saveImageInLocalGallery(Image image, String url);
}
