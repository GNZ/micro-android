package com.gang.micro.core.gallery.remote;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gang.micro.R;
import com.gang.micro.core.gallery.common.GalleryFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RemoteGalleryFragment extends Fragment implements GalleryFragment {

    @Bind(R.id.remote_gallery_list)
    RecyclerView gridView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Create adapter
        RemoteGalleryAdapter remoteGalleryAdapter = new RemoteGalleryAdapter(getActivity(),this);

        // Create layout manager
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);

        // Set the layout manager
        gridView.setLayoutManager(layoutManager);

        // Set adapter
        gridView.setAdapter(remoteGalleryAdapter);

        // Set item click listener
        // TODO: remoteGalleryAdapter.setOnItemClickListener(new MicroscopeListItemClickListener(getActivity(), remoteGalleryAdapter));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_remote_gallery, container, false);

        // Bind components
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void updateUI() {

    }
}
