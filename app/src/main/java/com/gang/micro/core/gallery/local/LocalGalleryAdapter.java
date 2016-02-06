package com.gang.micro.core.gallery.local;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.gang.micro.core.gallery.common.GalleryAdapter;
import com.gang.micro.core.gallery.common.GalleryFragment;
import com.gang.micro.core.gallery.common.GalleryItemViewHolder;
import com.gang.micro.core.gallery.common.item.GalleryItem;
import com.gang.micro.core.image.Image;
import com.gang.micro.core.utils.io.ImageIO;

import java.io.File;

public class LocalGalleryAdapter extends GalleryAdapter {

    public LocalGalleryAdapter(Context context, GalleryFragment fragment) {
        super(context, fragment);
    }

    @Override
    public String picturePath(int position) {
        Uri uri = Uri.fromFile(new File(ImageIO.getPicturePath(getDataset().get(position))));
        return uri.toString();
    }

    @Override
    public GalleryItem getItemClickListener(GalleryItemViewHolder galleryItemViewHolder) {
        return new GalleryItemLocal(galleryItemViewHolder, fragment);
    }

    @Override
    public void loadImages() {
        LocalImageLoadAsyncTask loadLocalImage = new LocalImageLoadAsyncTask(getContext(), this);
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
