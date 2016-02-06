package com.gang.micro.core.gallery.common.item;

import android.support.v4.app.Fragment;
import android.view.View;

import com.gang.micro.core.gallery.common.GalleryItemViewHolder;
import com.gang.micro.core.image.Image;

public abstract class GalleryItem implements View.OnClickListener {

    protected final GalleryItemViewHolder galleryItemViewHolder;
    protected Fragment fragment;

    public GalleryItem(GalleryItemViewHolder galleryItemViewHolder, Fragment fragment) {
        this.galleryItemViewHolder = galleryItemViewHolder;
        this.fragment = fragment;
    }

    public void updateItem() {
        galleryItemViewHolder.updateInAdapter();
    }

    public void removeItem() {
        galleryItemViewHolder.removeFromAdapter();
    }

    public Image getImage() {
        return galleryItemViewHolder.getImage();
    }

    public Fragment getFragment() {
        return fragment;
    }

    public abstract String getUrl();

    @Override
    public void onClick(View v) {
        GalleryItemFragment galleryItemFragment = new GalleryItemFragment();

        galleryItemFragment.setCaller(this);

        galleryItemFragment.show(fragment.getFragmentManager(), "galleryItemDetail");
    }

}
