package com.gang.micro.core.gallery.local;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.gang.micro.core.gallery.common.GalleryAdapter;
import com.gang.micro.core.gallery.common.GalleryFragment;
import com.gang.micro.core.gallery.common.item.GalleryItem;
import com.gang.micro.core.gallery.common.item.GalleryItemViewHolder;
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
    public void deleteImage(final int position) {

        (new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                Image image = dataset.get(position);
                return ImageIO.deletePicture(image) && ImageIO.deleteImage(image);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean)
                    remove(position);
            }
        }).execute();

    }

    @Override
    public void updateImage(final int position) {

        (new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                Image image = dataset.get(position);
                return ImageIO.saveImage(image);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean)
                    update(position);
            }
        }).execute();

    }

    @Override
    public void add(Image image) {
        if (!dataset.contains(image))
            super.add(image);
    }
}
