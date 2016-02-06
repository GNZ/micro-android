package com.gang.micro.core.gallery.local;

import android.net.Uri;
import android.support.v4.app.Fragment;

import com.gang.micro.core.gallery.common.GalleryItemViewHolder;
import com.gang.micro.core.gallery.common.item.GalleryItemImpl;
import com.gang.micro.core.utils.io.ImageIO;

import java.io.File;


public class LocalGalleryItemImpl extends GalleryItemImpl {
    public LocalGalleryItemImpl(GalleryItemViewHolder galleryItemViewHolder, Fragment fragment) {
        super(galleryItemViewHolder, fragment);
    }

    @Override
    public String getUrl() {
        Uri uri = Uri.fromFile(new File(ImageIO.getPicturePath(getImage())));
        return uri.toString();
    }
}
