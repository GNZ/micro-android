package com.gang.micro.core.gallery.common.item;

import android.view.View;

import com.gang.micro.core.image.Image;

import java.io.File;


public interface GalleryItem extends View.OnClickListener {

    void updateItem(Image newImage);

    void removeItem();

    Image getImage();

    File getPictureFile();

    String getUrl();
}
