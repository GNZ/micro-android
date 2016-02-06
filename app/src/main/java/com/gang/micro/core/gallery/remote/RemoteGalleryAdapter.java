package com.gang.micro.core.gallery.remote;

import android.content.Context;
import android.graphics.Bitmap;

import com.gang.micro.core.gallery.common.GalleryAdapter;
import com.gang.micro.core.gallery.common.GalleryItemViewHolder;
import com.gang.micro.core.gallery.common.item.GalleryItemImpl;
import com.gang.micro.core.image.Image;
import com.gang.micro.core.utils.image.ImageUtils;

public class RemoteGalleryAdapter extends GalleryAdapter {

    public RemoteGalleryAdapter(Context context, RemoteGalleryFragment fragment) {
        super(context, fragment);
    }

    @Override
    public String picturePath(int position) {
        return new ImageUtils(getContext()).getImageUrl(getDataset().get(position));
    }

    @Override
    public GalleryItemImpl getItemClickListener(GalleryItemViewHolder galleryItemViewHolder) {
        return new GalleryItemImplRemote(galleryItemViewHolder, fragment);
    }

    @Override
    public void loadImages() {
        RemoteImageLoadAsyncTask loadRemoteImages = new RemoteImageLoadAsyncTask(getContext(), this);

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
