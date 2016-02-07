package com.gang.micro.core.gallery.common.item;

import android.graphics.Bitmap;
import android.view.View;

import com.gang.micro.core.image.Image;


public interface GalleryItem extends View.OnClickListener {

    void updateItem(Image newImage);

    void removeItem();

    Image getImage();

    Bitmap getBitmap();

    String getUrl();
}
