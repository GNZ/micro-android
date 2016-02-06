package com.gang.micro.core.gallery.common.item;

import android.util.Log;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import com.gang.micro.R;


public class GalleryItemMenuItemClickListener implements Toolbar.OnMenuItemClickListener {

    private GalleryItem caller;
    private GalleryItemFragment galleryItemFragment;

    public GalleryItemMenuItemClickListener(GalleryItem caller, GalleryItemFragment galleryItemFragment){
        this.caller = caller;
        this.galleryItemFragment = galleryItemFragment;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.gallery_item_detail_edit_menu_button:
                //TODO
                return true;
            case R.id.gallery_item_detail_delete_menu_button:
                caller.removeItem();
                galleryItemFragment.dismiss();
                return true;
            default:
                return false;
        }
    }
}
