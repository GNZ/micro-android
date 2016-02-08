package com.gang.micro.ui;


import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.gang.micro.R;
import com.gang.micro.core.api.MicroApi;
import com.gang.micro.core.image.Image;
import com.gang.micro.core.image.ImageCaptureAsyncTask;
import com.gang.micro.core.image.ImageContainer;
import com.gang.micro.core.utils.image.ImageUtils;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CapturedImageFragment extends DialogFragment implements ImageContainer {

    MicroApi microApi;

    @Bind(R.id.fragment_captured_image_image_view)
    ImageView capturedImage;

    //@Bind(R.id.loading_image)
    ProgressBar loadingImage;

    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        context = getActivity().getApplicationContext();

        new ImageCaptureAsyncTask(context, this).execute();
    }

    @Override
    public void setImage(Image image) {

        String imageUrl = new ImageUtils(context).getImageUrl(image);

        // Set bitmap asynchronously
        Picasso.with(context)
                .load(imageUrl)
                .into(capturedImage);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_captured_image, container, false);

        // Bind components
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate();
    }
}
