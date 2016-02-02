package com.gang.micro.core.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.gang.micro.core.image.Image;
import com.gang.micro.core.utils.io.ImageIO;

import java.io.IOException;
import java.util.List;

public class LocalGalleryAdapter extends GalleryAdapter {
    public LocalGalleryAdapter(Context context, int resource, GalleryFragment fragment) {
        super(context, resource, fragment);
    }

    GalleryAdapter me = this;

    @Override
    public void loadImages() {

        AsyncTask<Void, Void, List<Image>> asyncTask = new AsyncTask<Void, Void, List<Image>>() {

            @Override
            protected List<Image> doInBackground(Void... voids) {
                ImageIO io = new ImageIO();

                List<Image> images = null;

                try {
                    images = io.readImages();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return images;
            }

            @Override
            protected void onPostExecute(List<Image> images) {
                if (images != null) {
                    me.addAll(images);
                    me.notifyDataSetChanged();
                }
            }
        };

        asyncTask.execute();
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
