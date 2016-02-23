package com.gang.micro.gallery.remote;

import com.gang.micro.gallery.common.GalleryFragment;
import com.gang.micro.gallery.common.item.GalleryItemImpl;
import com.gang.micro.gallery.common.item.GalleryItemViewHolder;
import com.gang.micro.utils.image.ImageUtils;


public class RemoteGalleryItemImpl extends GalleryItemImpl {

    public RemoteGalleryItemImpl(GalleryItemViewHolder galleryItemViewHolder, GalleryFragment fragment) {
        super(galleryItemViewHolder, fragment);
        galleryItemFragment = new RemoteGalleryItemFragment();
    }

    @Override
    public String getUrl() {
        return new ImageUtils(fragment.getActivity().getApplicationContext())
                .getImageUrl(getImage());
    }
}
