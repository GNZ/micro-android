package com.gang.micro.core.gallery.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gang.micro.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public abstract class GalleryFragment extends Fragment {

    @Bind(R.id.gallery_list)
    protected RecyclerView recyclerView;

    @Bind(R.id.gallery_empty)
    protected TextView empty;

    @Bind(R.id.gallery_loading_bar)
    protected ProgressBar loadingBar;

    protected GalleryAdapter galleryAdapter;

    protected RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        // Bind components
        ButterKnife.bind(this, view);

        return view;
    }

    public void updateUI() {
        loadingBar.setVisibility(View.GONE);
        if (recyclerView.getAdapter().getItemCount() == 0)
            empty.setVisibility(View.VISIBLE);
    }

}
