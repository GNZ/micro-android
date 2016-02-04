package com.gang.micro.core.gallery.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.gang.micro.core.gallery.fragments.GalleryFragment;
import com.gang.micro.core.gallery.imageload.LocalImageLoadAsyncTask;
import com.gang.micro.core.image.Image;
import com.gang.micro.core.utils.io.ImageIO;

import java.io.File;

public class LocalGalleryAdapter extends GalleryAdapter {

    public LocalGalleryAdapter(Context context, GalleryFragment fragment) {
        super(context, fragment);
    }

    @Override
    public String picturePath(int position) {
        Uri uri = Uri.fromFile(new File(ImageIO.getPicturePath(dataset.get(position))));
        return uri.toString();
    }

    @Override
    public void loadImages() {
        LocalImageLoadAsyncTask loadLocalImage = new LocalImageLoadAsyncTask(context,this);
        loadLocalImage.execute();
    }

    @Override
    public void deleteImage(Image image) {

    }

    @Override
    public void updateImage(Image image) {

    }

    @Override
    public void saveImage(Bitmap bitmap, Image image) {

    }
}
