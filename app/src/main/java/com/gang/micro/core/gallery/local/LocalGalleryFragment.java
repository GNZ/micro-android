package com.gang.micro.core.gallery.local;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gang.micro.R;
import com.gang.micro.core.gallery.common.GalleryAdapter;
import com.gang.micro.core.gallery.common.GalleryFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LocalGalleryFragment extends Fragment implements GalleryFragment {

    @Bind(R.id.local_gallery_list)
    RecyclerView recyclerView;

    @Bind(R.id.local_gallery_empty)
    TextView empty;

    @Bind(R.id.local_gallery_loading_bar)
    ProgressBar loadingBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Create adapter
        GalleryAdapter galleryAdapter = new LocalGalleryAdapter(getActivity(), this);

        // Set adapter to recyclerView
        recyclerView.setAdapter(galleryAdapter);

        // Create layout manager
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);

        recyclerView.setLayoutManager(layoutManager);
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
    public void updateUI() {
        loadingBar.setVisibility(View.GONE);
        if (recyclerView.getAdapter().getItemCount() == 0)
            empty.setVisibility(View.VISIBLE);
    }
}
