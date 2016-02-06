package com.gang.micro.core.gallery.local;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;

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
        return new LocalGalleryItemImpl(galleryItemViewHolder, fragment);
    }

    @Override
    public void loadImages() {
        LocalImageLoadAsyncTask loadLocalImage = new LocalImageLoadAsyncTask(getContext(), this);
        loadLocalImage.execute();
    }

    @Override
    public void deleteImage(int position) {

        (new AsyncTask<Integer, Void, Boolean>() {
            Image image;
            int position;
            @Override
            protected Boolean doInBackground(Integer... params) {
                position = params[0];
                image = dataset.get(position);
                return ImageIO.deletePicture(image) && ImageIO.deleteImage(image);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean)
                    remove(position);
            }
        }).execute(position);

    }

    @Override
    public void updateImage(int position, Image newImage) {

        (new AsyncTask<Object, Void, Boolean>() {
            Image image;
            int position;
            @Override
            protected Boolean doInBackground(Object... params) {
                position = (Integer) params[0];
                image = (Image) params[1];
                return ImageIO.saveImage(image);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean)
                    update(position);
            }
        }).execute(position,newImage);

    }

    @Override
    public void saveImage(Bitmap bitmap, Image image) {
        // NO IMPLEMENTATION
    }
}
