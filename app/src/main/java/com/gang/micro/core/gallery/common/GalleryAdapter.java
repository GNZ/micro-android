package com.gang.micro.core.gallery.common;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gang.micro.R;
import com.gang.micro.core.gallery.common.item.GalleryItem;
import com.gang.micro.core.image.Image;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public abstract class GalleryAdapter extends RecyclerView.Adapter<GalleryItemViewHolder> implements GalleryService {

    private GalleryFragment fragment;
    private Context context;
    private List<Image> dataset;

    public GalleryAdapter(Context context, GalleryFragment fragment) {
        this.context = context;
        this.fragment = fragment;

        dataset = new ArrayList<>();
        loadImages();
    }

    public GalleryItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gallery_list_item, parent, false);

        return new GalleryItemViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(GalleryItemViewHolder holder, int position) {

        String imageUrl = picturePath(position);

        holder.imageView.setImageDrawable(null);

        Picasso.with(context).load(imageUrl).into(holder.imageView);
        holder.textView.setText(dataset.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void add(Image image) {
        dataset.add(image);
        notifyItemInserted(dataset.size() - 1);
        fragment.updateUI();
    }

    public abstract String picturePath(int position);

    public GalleryItem getItemClickListener(GalleryItemViewHolder galleryItemViewHolder) {
        return new GalleryItemImpl(galleryItemViewHolder, (Fragment) fragment);
    }

    public GalleryFragment getFragment() {
        return fragment;
    }

    public Context getContext() {
        return context;
    }

    public List<Image> getDataset() {
        return dataset;
    }
}
