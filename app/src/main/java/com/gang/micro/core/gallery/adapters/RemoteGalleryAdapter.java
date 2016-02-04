package com.gang.micro.core.gallery.adapters;

import android.content.Context;
import android.graphics.Bitmap;

import com.gang.micro.core.gallery.imageload.RemoteImageLoadAsyncTask;
import com.gang.micro.core.gallery.fragments.RemoteGalleryFragment;
import com.gang.micro.core.image.Image;
import com.gang.micro.core.utils.image.ImageUtils;

public class RemoteGalleryAdapter extends GalleryAdapter {

    public RemoteGalleryAdapter(Context context, RemoteGalleryFragment fragment) {
        super(context, fragment);
    }

    @Override
    public String picturePath(int position) {
        return new ImageUtils(context).getImageUrl(dataset.get(position));
    }

    @Override
    public void loadImages() {
        RemoteImageLoadAsyncTask loadRemoteImages = new RemoteImageLoadAsyncTask(context, this);

        loadRemoteImages.execute();
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
