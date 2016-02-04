package com.gang.micro.core.gallery.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gang.micro.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @Bind(R.id.gallery_list_item_title)
    public TextView textView;

    @Bind(R.id.gallery_list_item_image)
    public ImageView imageView;

    public ImageHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onClick(View view) {

    }
}
