package com.gang.micro.gallery.common.item;

import android.support.v4.app.Fragment;
import android.view.View;

import com.gang.micro.image.Image;

public abstract class GalleryItemImpl implements GalleryItem {

    protected final GalleryItemViewHolder galleryItemViewHolder;
    protected Fragment fragment;
    protected GalleryItemFragment galleryItemFragment;

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

    @Override
    public void onClick(View v) {

        if (galleryItemFragment != null) {

            galleryItemFragment.setCaller(this);

            galleryItemFragment.show(fragment.getFragmentManager(), "galleryItemDetail");
        }
    }

}
