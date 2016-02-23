package com.gang.micro.gallery.local;

import android.content.Context;
import android.os.AsyncTask;

import com.gang.micro.gallery.common.GalleryAdapter;
import com.gang.micro.image.Image;
import com.gang.micro.utils.io.ImageIO;

import java.io.IOException;
import java.util.List;

public class LocalImageLoadAsyncTask extends AsyncTask<Void,Image,Void> {

    protected final Context context;
    protected final GalleryAdapter adapter;

    public LocalImageLoadAsyncTask(Context context, GalleryAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    protected Void doInBackground(Void... params) {
        List<String> filenames =  ImageIO.getFilenames(ImageIO.FOLDER);
        for(String f: filenames){
            try {
                Image image = ImageIO.readImage(f);
                publishProgress(image);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return null;
    }

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
