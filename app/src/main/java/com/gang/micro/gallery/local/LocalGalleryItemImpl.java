package com.gang.micro.gallery.local;

import android.net.Uri;
import android.support.v4.app.Fragment;

import com.gang.micro.gallery.common.item.GalleryItemFragment;
import com.gang.micro.gallery.common.item.GalleryItemImpl;
import com.gang.micro.gallery.common.item.GalleryItemViewHolder;
import com.gang.micro.utils.io.ImageIO;

import java.io.File;


public class LocalGalleryItemImpl extends GalleryItemImpl {
    public LocalGalleryItemImpl(GalleryItemViewHolder galleryItemViewHolder, Fragment fragment) {
        super(galleryItemViewHolder, fragment);
        galleryItemFragment = new GalleryItemFragment();
    }

    @Override
    public String getUrl() {
        Uri uri = Uri.fromFile(new File(ImageIO.getPicturePath(getImage())));
        return uri.toString();
    }
}
