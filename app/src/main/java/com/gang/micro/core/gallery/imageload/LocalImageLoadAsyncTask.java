package com.gang.micro.core.gallery.imageload;

import android.content.Context;

import com.gang.micro.core.gallery.adapters.GalleryAdapter;
import com.gang.micro.core.image.Image;
import com.gang.micro.core.utils.io.ImageIO;

import java.io.IOException;
import java.util.List;

public class LocalImageLoadAsyncTask extends ImageLoadAsyncTask {

    public LocalImageLoadAsyncTask(Context context, GalleryAdapter adapter) {
        super(context, adapter);
    }

    @Override
    protected Void doInBackground(Void... params) {
        ImageIO io = new ImageIO();
        List<String> filenames =  io.getFilenames(ImageIO.FOLDER);
        for(String f: filenames){
            try {
                Image image = io.readImage(f);
                publishProgress(image);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return null;
    }

}
