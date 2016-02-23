package com.gang.micro.gallery.common.item;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gang.micro.R;
import com.gang.micro.image.Image;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class GalleryItemAttributeListAdapter extends RecyclerView.Adapter<GalleryItemListAttributeVH> {

    private final Context context;
    private final Image image;
    private List<GalleryItemAttribute> attributes;

    public GalleryItemAttributeListAdapter(Context context, Image image) {
        this.context = context;
        this.image = image;

        loadData();
    }

    private void loadData() {

        attributes = new ArrayList<>();

        // TODO: What to do with strings?
        // Add Name
        attributes.add(new GalleryItemAttribute(image.getName(), "Nombre"));

        // Add description
        attributes.add(new GalleryItemAttribute(image.getDescription(), "Descripción"));

        // Add date
        String formattedDate = new SimpleDateFormat("d MMM yyyy HH:mm:ss").format(image.getCreated_at());

        attributes.add(new GalleryItemAttribute(formattedDate, "Fecha de captura"));

        notifyDataSetChanged();
    }

    @Override
    public GalleryItemListAttributeVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_gallery_item_detail, parent, false);

        return new GalleryItemListAttributeVH(view);
    }

    @Override
    public void onBindViewHolder(GalleryItemListAttributeVH holder, int position) {
        holder.titleView.setText(attributes.get(position).getName());
        holder.subtitleView.setText(attributes.get(position).getValue());
    }

    @Override
    public int getItemCount() {
        return attributes.size();
    }
}
