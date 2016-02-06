package com.gang.micro.core.gallery.common.item;

import android.content.Intent;
import android.net.Uri;
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
            case R.id.gallery_item_detail_share_menu_button:
                callSharePictureIntent();
                return true;
            default:
                return false;
        }
    }

    private void callSharePictureIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/jpeg");
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(caller.getPictureFile()));
        galleryItemFragment.startActivityForResult(shareIntent,galleryItemFragment.SHARE_INTENT_CODE);
    }
}
