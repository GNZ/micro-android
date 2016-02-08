package com.gang.micro.core.gallery.common.item;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gang.micro.R;
import com.gang.micro.core.image.Image;
import com.gang.micro.core.utils.io.ImageIO;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GalleryItemFragment extends DialogFragment {

    public static final int SHARE_INTENT_CODE = 2;

    // UI elements

    @Bind(R.id.view_analyses_imageView)
    ImageView imageView;

    @Bind(R.id.fragment_gallery_item_attribute_list)
    RecyclerView attributesList;

    @Bind(R.id.fragment_gallery_item_toolbar)
    Toolbar toolbar;

    protected GalleryItem caller;

    protected Image image;

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

        return initUI(rootView);
    }

    protected View initUI(View rootView){
        toolbar.inflateMenu(R.menu.menu_gallery_item_detail);

        toolbar.setOnMenuItemClickListener(new GalleryItemMenuItemClickListener(caller,this));

        if (caller == null) {
            Log.e(this.getClass().getName(), "No image to display");
            return rootView;
        }

        image = caller.getImage();

        // Load image
        String url = caller.getUrl();

        Picasso.with(getContext()).load(url).into(imageView);

        attributesList.setAdapter(new GalleryItemAttributeListAdapter(getContext(), image));

        attributesList.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SHARE_INTENT_CODE)
            ImageIO.deletePicture(ImageIO.TEMP_PICTURE_FILE);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void refresh(){
        attributesList.setAdapter(new GalleryItemAttributeListAdapter(getContext(), image));
    }

    public void setCaller(GalleryItem caller) {
        this.caller = caller;
    }

    public GalleryItem getCaller() {
        return caller;
    }
}
