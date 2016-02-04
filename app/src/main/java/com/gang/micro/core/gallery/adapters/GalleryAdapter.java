package com.gang.micro.core.gallery.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gang.micro.R;
import com.gang.micro.core.gallery.fragments.GalleryFragment;
import com.gang.micro.core.image.Image;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public abstract class GalleryAdapter extends RecyclerView.Adapter<ImageHolder> implements GalleryService {


    GalleryFragment fragment;
    Context context;
    ArrayList<Image> dataset;

    public GalleryAdapter(Context context, GalleryFragment fragment) {
        this.context = context;
        this.fragment = fragment;

        dataset = new ArrayList<>();
        loadImages();
    }

    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gallery_list_item, parent, false);

        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageHolder holder, int position) {

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

}
