package com.gang.micro.core.gallery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.gang.micro.R;
import com.gang.micro.core.microscope.Microscope;
import com.gang.micro.ui.ViewAnalysisActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LocalGalleryFragment extends Fragment {

    @Bind(R.id.gridView)
    GridView gridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Create adapter
        GalleryAdapter galleryAdapter = new LocalGalleryAdapter(getActivity(), R.layout.gallery_grid_item);

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

    private class onImageListItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

            // Get context
            Context context = adapterView.getContext();

            // Load clicked microscope
            Microscope microscope = (Microscope) adapterView.getItemAtPosition(position);

            // Create PreviewActivity intent
            Intent intent = new Intent(context, ViewAnalysisActivity.class);

            // Put microscope_ip as extra
            intent.putExtra(ViewAnalysisActivity.EXTRA_IMAGE_ID, microscope.getIp());

            // Start activity
            context.startActivity(intent);
        }
    }
}
