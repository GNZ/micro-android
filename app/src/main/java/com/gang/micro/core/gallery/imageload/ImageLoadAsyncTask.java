package com.gang.micro.core.gallery.imageload;


import android.content.Context;
import android.os.AsyncTask;

import com.gang.micro.core.gallery.adapters.GalleryAdapter;
import com.gang.micro.core.image.Image;

public abstract class ImageLoadAsyncTask extends AsyncTask<Void, Image, Void> {

    final Context context;
    final GalleryAdapter adapter;

    public ImageLoadAsyncTask(Context context, GalleryAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    protected abstract Void doInBackground(Void... params);

    @Override
    protected void onProgressUpdate(Image... images) {
        super.onProgressUpdate(images);

        adapter.add(images[0]);
    }

}
