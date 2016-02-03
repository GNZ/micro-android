package com.gang.micro.core.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gang.micro.R;
import com.gang.micro.core.image.Image;
import com.gang.micro.core.microscope.Microscope;
import com.gang.micro.ui.ViewAnalysisActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LocalGalleryFragment extends Fragment implements GalleryFragment {

    @Bind(R.id.local_gallery_gridView)
    GridView gridView;

    @Bind(R.id.local_gallery_empty)
    TextView empty;

    @Bind(R.id.local_gallery_loading_bar)
    ProgressBar loadingBar;

    GalleryWrapper listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Create adapter
        GalleryAdapter galleryAdapter = new LocalGalleryAdapter(getActivity(), R.layout.gallery_grid_item, this);

        // Set adapter to grid
        gridView.setAdapter(galleryAdapter);

        // Set click listener
        gridView.setOnItemClickListener(new onImageListItemClick());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_local_gallery, container, false);

        // Bind components
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (GalleryWrapper) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void updateUI() {
        loadingBar.setVisibility(View.GONE);
        if (gridView.getAdapter().isEmpty())
            empty.setVisibility(View.VISIBLE);
    }

    private class onImageListItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

            Image image = (Image) adapterView.getItemAtPosition(position);

            listener.showImage(image);
        }
    }
}
