package com.gang.micro.core.gallery.common;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gang.micro.R;
import com.gang.micro.core.image.Image;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GalleryItemViewHolder extends RecyclerView.ViewHolder {

    private GalleryAdapter adapter;

    @Bind(R.id.gallery_list_item_title)
    public TextView textView;

    @Bind(R.id.gallery_list_item_image)
    public ImageView imageView;

    public GalleryItemViewHolder(View itemView, GalleryAdapter adapter) {
        super(itemView);

        this.adapter = adapter;

        itemView.setOnClickListener(adapter.getItemClickListener(this));

        ButterKnife.bind(this, itemView);
    }

    public void removeFromAdapter() {
        int adapterPosition = getAdapterPosition();

        adapter.getDataset().remove(adapterPosition);

        adapter.notifyItemRemoved(adapterPosition);
    }

    public void updateInAdapter() {
        int adapterPosition = getAdapterPosition();

        adapter.notifyItemChanged(adapterPosition);
    }

    public Image getImage() {

        return adapter.getDataset().get(getAdapterPosition());
    }
}
