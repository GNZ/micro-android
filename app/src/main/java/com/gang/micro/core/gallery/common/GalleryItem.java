package com.gang.micro.core.gallery.common;

import android.view.View;

public interface GalleryItem extends View.OnClickListener {

    void updateItem();

    void removeItem();
}
