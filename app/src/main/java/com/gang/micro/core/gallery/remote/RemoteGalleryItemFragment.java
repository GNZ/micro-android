package com.gang.micro.core.gallery.remote;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gang.micro.R;
import com.gang.micro.core.gallery.common.item.GalleryItemFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RemoteGalleryItemFragment extends GalleryItemFragment {

    RemoteGalleryItemFragmentListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_remote_image_detail, container, false);
        ButterKnife.bind(this, rootView);

        return initUI(rootView);
    }

    @Override
    public void onAttach(Activity activity) {
        listener = (RemoteGalleryItemFragmentListener) activity;
        super.onAttach(activity);
    }

    @OnClick(R.id.view_analysis_save_fab)
    void saveInLocalGallery() {
        listener.saveImageInLocalGallery(image,caller.getUrl());
    }


}
