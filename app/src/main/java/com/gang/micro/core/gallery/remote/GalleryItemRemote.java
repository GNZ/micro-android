package com.gang.micro.core.gallery.remote;

import com.gang.micro.core.gallery.common.GalleryFragment;
import com.gang.micro.core.gallery.common.GalleryItemViewHolder;
import com.gang.micro.core.gallery.common.item.GalleryItem;
import com.gang.micro.core.utils.image.ImageUtils;


public class GalleryItemRemote extends GalleryItem {

    public GalleryItemRemote(GalleryItemViewHolder galleryItemViewHolder, GalleryFragment fragment) {
        super(galleryItemViewHolder, fragment);
    }

    @Override
    public String getUrl() {
        return new ImageUtils(fragment.getActivity().getApplicationContext())
                .getImageUrl(getImage());
    }
}
