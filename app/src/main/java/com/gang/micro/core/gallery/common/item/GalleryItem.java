package com.gang.micro.core.gallery.common.item;

import android.support.v4.app.Fragment;
import android.view.View;

import com.gang.micro.core.image.Image;


public interface GalleryItem extends View.OnClickListener {
    void updateItem();

    void removeItem();

    Image getImage();

    Fragment getFragment();

    String getUrl();

    @Override
    void onClick(View v);
}
