package com.gang.micro.gallery.common.item;

import android.view.View;

import com.gang.micro.image.Image;


public interface GalleryItem extends View.OnClickListener {

    void updateItem();

    void removeItem();

    Image getImage();

    String getUrl();
}
