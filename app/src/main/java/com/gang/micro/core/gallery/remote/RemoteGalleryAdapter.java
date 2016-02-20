package com.gang.micro.core.gallery.remote;

import android.content.Context;

import com.gang.micro.core.api.MicroApi;
import com.gang.micro.core.gallery.common.GalleryAdapter;
import com.gang.micro.core.gallery.common.item.GalleryItem;
import com.gang.micro.core.gallery.common.item.GalleryItemViewHolder;
import com.gang.micro.core.image.Image;
import com.gang.micro.core.utils.api.ErrorLoggingCallback;
import com.gang.micro.core.utils.image.ImageUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RemoteGalleryAdapter extends GalleryAdapter {

    public RemoteGalleryAdapter(Context context, RemoteGalleryFragment fragment) {
        super(context, fragment);
    }

    @Override
    public String picturePath(int position) {
        return new ImageUtils(getContext()).getImageUrl(getDataset().get(position));
    }

    @Override
    public GalleryItem getItemClickListener(GalleryItemViewHolder galleryItemViewHolder) {
        return new RemoteGalleryItemImpl(galleryItemViewHolder, fragment);
    }

    @Override
    public void loadImages() {
        new MicroApi(context).getApi().getImages().enqueue(new ErrorLoggingCallback<List<Image>>() {
            @Override
            public void onSuccessfulResponse(Response<List<Image>> response, Retrofit retrofit) {
                List<Image> images = response.body();
                for (Image image : images) {
                    if (!dataset.contains(image))
                        add(image);
                }
                fragment.updateUI();
            }
        });
    }

    @Override
    public void deleteImage(final int position) {
        Image image = dataset.get(position);

        Call<Boolean> call = new MicroApi(getContext())
                .getApi()
                .deleteImage(image.getId());

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Response<Boolean> response, Retrofit retrofit) {
                boolean success = response.body();
                if (success)
                    remove(position);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });

    }

    @Override
    public void updateImage(final int position) {

        Image image = dataset.get(position);

        Call<Image> call = new MicroApi(getContext())
                .getApi()
                .updateImage(image.getId(), image);

        call.enqueue(new Callback<Image>() {
            @Override
            public void onResponse(Response<Image> response, Retrofit retrofit) {
                update(position);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }
}
