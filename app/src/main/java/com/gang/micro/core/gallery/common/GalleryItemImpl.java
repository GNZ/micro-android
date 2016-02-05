package com.gang.micro.core.gallery.common;

import android.support.v4.app.Fragment;
import android.view.View;

import com.gang.micro.core.gallery.common.item.GalleryItem;
import com.gang.micro.core.gallery.common.item.GalleryItemFragment;
import com.gang.micro.core.image.Image;

public class GalleryItemImpl implements GalleryItem {

    private final GalleryItemViewHolder galleryItemViewHolder;
    private Fragment fragment;

    public GalleryItemImpl(GalleryItemViewHolder galleryItemViewHolder, Fragment fragment) {
        this.galleryItemViewHolder = galleryItemViewHolder;
        this.fragment = fragment;
    }

    @Override
    public void updateItem() {
        galleryItemViewHolder.updateInAdapter();
    }

    @Override
    public void removeItem() {
        galleryItemViewHolder.removeFromAdapter();
    }

    @Override
    public Image getImage() {
        return galleryItemViewHolder.getImage();
    }

    public Fragment getFragment() {
        return fragment;
    }

    @Override
    public void onClick(View v) {
        GalleryItemFragment galleryItemFragment = new GalleryItemFragment();

        galleryItemFragment.setCaller(this);

        galleryItemFragment.show(fragment.getFragmentManager(), "galleryItemDetail");
    }
}
