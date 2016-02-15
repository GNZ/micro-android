package com.gang.micro.core.gallery.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gang.micro.R;
import com.gang.micro.core.gallery.common.item.GalleryItem;
import com.gang.micro.core.gallery.common.item.GalleryItemViewHolder;
import com.gang.micro.core.image.Image;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public abstract class GalleryAdapter extends RecyclerView.Adapter<GalleryItemViewHolder> implements GalleryService {

    protected GalleryFragment fragment;
    protected Context context;
    protected List<Image> dataset;

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

    @Override
    public void finishedLoadingItems() {
        fragment.updateUI();
    }

    public void add(Image image) {
        dataset.add(image);
        notifyItemInserted(dataset.size() - 1);
        fragment.updateUI();
    }

    protected void remove(int position){
        dataset.remove(position);
        notifyItemRemoved(position);
    }

    protected void update(int position){
        notifyItemChanged(position,new Object());
    }

    public abstract String picturePath(int position);

    public abstract GalleryItem getItemClickListener(GalleryItemViewHolder galleryItemViewHolder);

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
