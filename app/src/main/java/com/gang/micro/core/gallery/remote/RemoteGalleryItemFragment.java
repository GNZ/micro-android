package com.gang.micro.core.gallery.remote;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gang.micro.R;
import com.gang.micro.core.MicroApplication;
import com.gang.micro.core.gallery.common.item.GalleryItemFragment;
import com.gang.micro.core.utils.io.ImageIO;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RemoteGalleryItemFragment extends GalleryItemFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_remote_image_detail, container, false);
        ButterKnife.bind(this, rootView);

        return initUI(rootView);
    }

    @OnClick(R.id.view_analysis_save_fab)
    void saveInLocalGallery() {

        Glide.with(getContext()).load(caller.getUrl()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                ImageIO.saveImage(image);
                ImageIO.savePicture(resource, image);

                String msg = getResources().getString(R.string.image_save) + " " + image.getName();

                Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();

                ((MicroApplication) getActivity().getApplication())
                        .getLocalGalleryAdapter()
                        .add(image);
            }
        });
    }
}
