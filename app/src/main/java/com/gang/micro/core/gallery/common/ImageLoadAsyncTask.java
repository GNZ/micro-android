package com.gang.micro.core.gallery.common;


import android.content.Context;
import android.os.AsyncTask;

import com.gang.micro.core.image.Image;

public abstract class ImageLoadAsyncTask extends AsyncTask<Void, Image, Void> {

    protected final Context context;
    protected final GalleryAdapter adapter;

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

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        adapter.finishedLoadingItems();
    }
}
