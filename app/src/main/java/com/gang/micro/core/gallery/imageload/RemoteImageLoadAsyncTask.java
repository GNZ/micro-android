package com.gang.micro.core.gallery.imageload;

import android.content.Context;

import com.gang.micro.core.api.MicroApi;
import com.gang.micro.core.api.MicroApiSpecification;
import com.gang.micro.core.gallery.adapters.GalleryAdapter;
import com.gang.micro.core.image.Image;
import com.gang.micro.core.utils.api.ErrorLoggingCallback;

import java.util.List;

import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

public class RemoteImageLoadAsyncTask extends ImageLoadAsyncTask {


    public RemoteImageLoadAsyncTask(Context context, GalleryAdapter adapter) {
        super(context, adapter);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        final MicroApiSpecification microApi = new MicroApi(context).getApi();

        Call<List<Image>> imagesCallback = microApi.getImages();

        imagesCallback.enqueue(new ErrorLoggingCallback<List<Image>>() {
            @Override
            public void onSuccessfulResponse(Response<List<Image>> response, Retrofit retrofit) {
                List<Image> images = response.body();

                for (Image image : images) {
                    publishProgress(image);
                }
            }
        });

        return null;
    }
}
