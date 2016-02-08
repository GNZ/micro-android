package com.gang.micro.core.gallery.common.item;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.gang.micro.core.image.Image;

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
    public Bitmap getBitmap() {
        return ((BitmapDrawable) galleryItemViewHolder.imageView.getDrawable())
                .getBitmap();
    }

    @Override
    public void onClick(View v) {

        if (galleryItemFragment != null) {

            galleryItemFragment.setCaller(this);

            galleryItemFragment.show(fragment.getFragmentManager(), "galleryItemDetail");
        }
    }

}
