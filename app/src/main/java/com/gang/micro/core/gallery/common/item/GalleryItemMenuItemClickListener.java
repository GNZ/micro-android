package com.gang.micro.core.gallery.common.item;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.gang.micro.R;
import com.gang.micro.core.utils.io.ImageIO;

import java.io.File;


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
                callEditImage();
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
        File bitmapFile = ImageIO.savePicture(caller.getBitmap(),ImageIO.TEMP_PICTURE_FILE);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(bitmapFile));
        galleryItemFragment.startActivityForResult(shareIntent,galleryItemFragment.SHARE_INTENT_CODE);
    }

    private void callEditImage() {
        GalleryItemEditFragment editDialogFragment = new GalleryItemEditFragment();
        editDialogFragment.setCaller(caller);
        editDialogFragment.setGalleryItemFragment(galleryItemFragment);
        editDialogFragment.show(galleryItemFragment.getFragmentManager(), "edit dialog");
    }
}
