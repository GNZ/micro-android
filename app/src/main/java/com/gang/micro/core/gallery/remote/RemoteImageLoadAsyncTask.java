package com.gang.micro.core.gallery.remote;

import android.content.Context;

import com.gang.micro.core.api.MicroApi;
import com.gang.micro.core.api.MicroApiSpecification;
import com.gang.micro.core.gallery.common.GalleryAdapter;
import com.gang.micro.core.gallery.common.ImageLoadAsyncTask;
import com.gang.micro.core.image.Image;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Response;

public class RemoteImageLoadAsyncTask extends ImageLoadAsyncTask {


    public RemoteImageLoadAsyncTask(Context context, GalleryAdapter adapter) {
        super(context, adapter);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        final MicroApiSpecification microApi = new MicroApi(context).getApi();

        Call<List<Image>> imagesCallback = microApi.getImages();

        try {
            Response<List<Image>> imagesResponse = imagesCallback.execute();

            List<Image> images = imagesResponse.body();

            if (images != null) {

                for (Image image : images) {
                    publishProgress(image);
                }
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
