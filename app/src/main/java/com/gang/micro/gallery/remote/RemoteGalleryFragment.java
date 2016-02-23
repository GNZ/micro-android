package com.gang.micro.gallery.remote;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.gang.micro.gallery.common.GalleryFragment;

public class RemoteGalleryFragment extends GalleryFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Create adapter
        galleryAdapter = new RemoteGalleryAdapter(getActivity(), this);

        // Create layout manager
        layoutManager = new GridLayoutManager(getActivity(), 2);

        // Set the layout manager
        recyclerView.setLayoutManager(layoutManager);

        // Set adapter
        recyclerView.setAdapter(galleryAdapter);
    }
}
