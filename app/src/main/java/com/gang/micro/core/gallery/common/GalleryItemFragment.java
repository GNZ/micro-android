package com.gang.micro.core.gallery.common;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gang.micro.R;
import com.gang.micro.core.image.Image;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GalleryItemFragment extends DialogFragment {

    private Image image;

    // UI elements
    @Bind(R.id.view_analyses_imageView)
    ImageView imageView;

    @Bind(R.id.view_analyses_name)
    TextView nameTextView;

    @Bind(R.id.view_analyses_date)
    TextView dateTextView;

    @Bind(R.id.view_analyses_description)
    TextView descriptionTextView;

    private GalleryItem caller;

    public GalleryItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_analysis, container, false);
        ButterKnife.bind(this, rootView);


        if (image != null) {
            imageView.setImageBitmap(image.getBitmap());
            nameTextView.setText(image.getId().toString().substring(0, 6));
            dateTextView.setText(image.getCreated_at().toString());
            descriptionTextView.setText(image.getAnalyses().get(0).getResult());
        } else {
            // TODO
        }
        return rootView;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setCaller(GalleryItem caller) {
        this.caller = caller;
    }

    public GalleryItem getCaller() {
        return caller;
    }
}
