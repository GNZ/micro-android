package com.gang.micro.gallery.common.item;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gang.micro.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GalleryItemListAttributeVH extends RecyclerView.ViewHolder {

    @Bind(R.id.list_item_title)
    public TextView titleView;

    @Bind(R.id.list_item_subtitle)
    public TextView subtitleView;

    public GalleryItemListAttributeVH(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }
}
