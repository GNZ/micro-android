package com.gang.micro.core.gallery.local;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.gang.micro.core.gallery.common.GalleryFragment;

public class LocalGalleryFragment extends GalleryFragment {


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Create adapter
        galleryAdapter = new LocalGalleryAdapter(getActivity(), this);

        // Create layout manager
        layoutManager = new GridLayoutManager(getActivity(), 2);

        // Set the layout manager
        recyclerView.setLayoutManager(layoutManager);

        // Set adapter to recyclerView
        recyclerView.setAdapter(galleryAdapter);
    }
}
