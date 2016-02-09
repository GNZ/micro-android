package com.gang.micro.core.gallery.remote;

import com.gang.micro.core.gallery.common.GalleryFragment;
<<<<<<< HEAD
import com.gang.micro.core.gallery.common.item.GalleryItemViewHolder;
import com.gang.micro.core.gallery.common.item.GalleryItemImpl;
=======
import com.gang.micro.core.gallery.common.item.GalleryItemImpl;
import com.gang.micro.core.gallery.common.item.GalleryItemViewHolder;
import com.gang.micro.core.gallery.common.item.RemoteGalleryItemFragment;
>>>>>>> 487f903... GalleryItemViewHolder moved
import com.gang.micro.core.utils.image.ImageUtils;


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
